package server.websocket;

import chess.*;
import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import dataAccess.UserDao;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.messages.Error;
import webSocketMessages.serverMessages.messages.LoadGame;
import webSocketMessages.serverMessages.messages.Notification;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.userCommands.commands.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

;

@WebSocket
public class WebSocketHandler {
  private final ConnectionManager connectionManager = new ConnectionManager();
  private UserDao userDao;
  private GameDao gameDao;
  private AuthDao authDao;

  private final Set<Integer> completedGames = new HashSet<>();

  public WebSocketHandler(UserDao userDao, GameDao gameDao, AuthDao authDao) {
    this.userDao=userDao;
    this.gameDao=gameDao;
    this.authDao=authDao;
  }


  @OnWebSocketConnect
  public void onConnect(Session session){
    System.out.println("New WebSocket Connection Established: " + session.getLocalAddress());
  }

  @OnWebSocketMessage
  public void onMessage(Session session, String msg) throws Exception {
    UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);

    String authToken = command.getAuthString();
    if(!authDao.verifyAuthToken(authToken)){
      session.getRemote().sendString(new Gson().toJson(new Error("Error"+msg)));
    }

    switch(command.getCommandType()){
      case JOIN_PLAYER -> join(msg,session,authToken);
      case JOIN_OBSERVER -> joinObserve(msg,session,authToken);
      case MAKE_MOVE -> makeMove(msg,session,authToken);
      case RESIGN -> resign(msg,session,authToken);
      case LEAVE -> leave(msg,session,authToken);
    }
  }

  private void leave(String msg, Session session, String authToken) {
    Leave leave = new Gson().fromJson(msg, Leave.class);
    try {
      int gameID = leave.getGameID();
      GameData gameData =gameDao.getGameData(gameID);
      String bUsername = gameData.getBlackUsername();
      String wUsername = gameData.getWhiteUsername();
      String name = gameData.getGameName();
      AuthData authData = authDao.getToken(authToken);
      String username = authData.getUsername();
      var message=String.format("%s has left the game", username);
      Notification notification = new Notification(message);
      connectionManager.broadcast(gameID,notification,authToken,false);
      connectionManager.removeSessionFromGame(gameID,authToken);
    } catch (Exception e) {

    }

  }

  private void resign(String msg, Session session, String authToken) throws IOException {
    Resign resign = new Gson().fromJson(msg, Resign.class);
    int gameID = resign.getGameID();
    try {
      GameData gameData =gameDao.getGameData(gameID);
      String bUsername = gameData.getBlackUsername();
      String wUsername = gameData.getWhiteUsername();
      String name = gameData.getGameName();
      AuthData authData = authDao.getToken(authToken);
      String username = authData.getUsername();

      if(completedGames.contains(gameID)){
        throw new DataAccessException(402, "The game is over");
      }

      if (!username.equals(bUsername) && !username.equals(wUsername)) {
        throw new DataAccessException(403, "Observer can't resign");
      }


      GameData completedGame = new GameData(gameID);

      gameDao.updateGameBoard(gameID,completedGame);

      completedGames.add(gameID);

      var message = String.format("A Player has resigned");
      var notification = new Notification(message);
      connectionManager.broadcast(gameID, notification, null,true);
    } catch (DataAccessException | IOException e) {
      e.printStackTrace();
      String errorMsg = e.getMessage();
      Error error = new Error(errorMsg);
      connectionManager.broadcastError(gameID,error,authToken);
    }

  }

  private void makeMove(String msg, Session session, String authToken) throws IOException, DataAccessException {
    MakeMove makeMove = new Gson().fromJson(msg, MakeMove.class);
    ChessMove move = makeMove.getChessMove();
    Integer gameID = makeMove.getGameId();
    try {
    if(completedGames.contains(gameID)){
      throw new DataAccessException(402, "The game is over");
    }

    AuthData authData = authDao.getToken(authToken);
    String username = authData.getUsername();
    GameData gameData = gameDao.getGameData(gameID);
    ChessGame game = gameData.getGame();
    if(game.isGameOver()){
      throw new DataAccessException(403,"Game is over");
    }
    if(game ==null){
      throw new DataAccessException(402, "The game is over");
    }
    String gameName = gameData.getGameName();
    String whiteUsername = gameData.getWhiteUsername();
    String blackUsername = gameData.getBlackUsername();
    ChessPiece piece = game.getBoard().getPiece(move.getStartPosition());
    ChessGame.TeamColor color = piece.getTeamColor();
      Collection<ChessMove> moveCollection = gameData.getGame().validMoves(move.getStartPosition());
        if(moveCollection.contains(move)){
          if(color == ChessGame.TeamColor.BLACK){
            if (!gameData.getBlackUsername().equals(username)) {
              throw new DataAccessException(402, "Move is invalid");
            }
          }else{
            if (!gameData.getWhiteUsername().equals(username)) {
              throw new DataAccessException(402, "Move is invalid");
            }
          }
          game.makeMove(move);

          ChessGame.TeamColor currentTeamColor = game.getTeamTurn();
          if (game.isInCheckmate(ChessGame.TeamColor.WHITE) || game.isInCheckmate(ChessGame.TeamColor.BLACK)) {
            completedGames.add(gameID);
          }
          GameData updatedGame = new GameData(gameID,whiteUsername,blackUsername,gameName,game);
          gameDao.updateGameBoard(gameID,updatedGame);
          connectionManager.broadcastGame(gameID,new LoadGame(game.getBoard()));
        }else{
          throw new DataAccessException(402,"Move is invalid");
        }
      var message = String.format("Player has made a move");
      var notification = new Notification(message);
      connectionManager.broadcast(gameID, notification, authToken,false);
    } catch (DataAccessException | InvalidMoveException e) {
      e.printStackTrace();
      String errorMsg = e.getMessage();
      session.getRemote().sendString(new Gson().toJson(new Error("Error"+errorMsg)));
    }
  }

  private void joinObserve(String msg, Session session, String authToken) throws IOException {
    try {
      JoinObserver joinObserver = new Gson().fromJson(msg, JoinObserver.class);
      AuthData authData = authDao.getToken(authToken);
      String username = authData.getUsername();
      Integer gameID = joinObserver.getGameID();
      GameData gameData = gameDao.getGameData(gameID);
      if(gameData==null){
        throw new DataAccessException(401,"GameID is bad");
      }
      ChessBoard game = gameData.getGame().getBoard();


      connectionManager.addSessionToGame(gameID, authToken, session);

      LoadGame loadGame = new LoadGame(game);
      session.getRemote().sendString(new Gson().toJson(loadGame));

      //session
      var message = String.format("%s has join the game as an Observer", username);
      var notification = new Notification(message);
      //exclude the client
      connectionManager.broadcast(gameID, notification, authToken,false);

    } catch (DataAccessException | IOException e) {
      e.printStackTrace();
      String errorMsg = e.getMessage();
      session.getRemote().sendString(new Gson().toJson(new Error("Error"+errorMsg)));
    }
  }


  public void join(String msg, Session session,String authToken) throws IOException {
    //call the api to verify game in db
    String color=null;
    try {
      JoinPlayer joinPlayer=new Gson().fromJson(msg, JoinPlayer.class);
      AuthData authData=authDao.getToken(authToken);
      String username=authData.getUsername();
      Integer gameID=joinPlayer.getGameID();
      ChessGame.TeamColor teamColor=joinPlayer.getPlayerColor();

      GameData gameData=gameDao.getGameData(gameID);

      if(gameData==null){
        throw new DataAccessException(401,"GameID is bad");
      }

      ChessBoard game=gameData.getGame().getBoard();

      connectionManager.addSessionToGame(gameID, authToken, session);


      if(teamColor == ChessGame.TeamColor.BLACK){
        color = "BLACK";
      }else{
        color="WHITE";
      }

      if(color =="WHITE"){
        if(gameData.getWhiteUsername() ==null){
          throw new DataAccessException(401,"Websocket was called before http");
        }else{
          if(gameData.getBlackUsername() ==null){
            throw new DataAccessException(401,"Websocket was called before http");
          }
        }

      }
//     JoinGameRequest joinGameRequest = new JoinGameRequest(color,String.valueOf(gameID));
      if ((!username.equals(gameData.getWhiteUsername()) && color.equals("WHITE")) ||
              (!username.equals(gameData.getBlackUsername()) && color.equals("BLACK"))) {
        throw new DataAccessException(403, "There is already a player joined");
      }


    //send load game message use gson and session.getRemote().sendString(msg);
      LoadGame loadGame=new LoadGame(game);
      session.getRemote().sendString(new Gson().toJson(loadGame));

      //session
      var message=String.format("%s has join the game as %s", username, teamColor);
      var notification=new Notification(message);
      //exclude the client
      connectionManager.broadcast(gameID,notification,authToken,false);
    }
    catch (DataAccessException e) {
      e.printStackTrace();
      msg =e.getMessage();
      session.getRemote().sendString(new Gson().toJson(new Error("Error"+msg)));
    }
  }






}
