package server.websocket;

import chess.ChessBoard;
import chess.ChessGame;
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
import webSocketMessages.userCommands.commands.JoinPlayer;

import java.io.IOException;

;

@WebSocket
public class WebSocketHandler {
  private final ConnectionManager connectionManager = new ConnectionManager();
  private final UserDao userDao;
  private final GameDao gameDao;
  private final AuthDao authDao;

  public WebSocketHandler(UserDao userDao, GameDao gameDao, AuthDao authDao) {
    this.userDao = userDao;
    this.gameDao = gameDao;
    this.authDao = authDao;
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
      connectionManager.broadcast(gameID,notification,authToken);
    }
    catch (DataAccessException e) {
      e.printStackTrace();
      msg =e.getMessage();
      session.getRemote().sendString(new Gson().toJson(new Error("Error"+msg)));
    }
  }




}
