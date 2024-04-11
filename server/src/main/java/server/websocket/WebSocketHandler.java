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
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.messages.LoadGame;
import webSocketMessages.serverMessages.messages.Notification;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.userCommands.commands.JoinPlayer;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
  private final ConnectionManager connectionManager = new ConnectionManager();
  private UserDao userDao;
  private GameDao gameDao;
  private AuthDao authDao;

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

    switch(command.getCommandType()){
//      case JOIN_OBSERVER -> join(session,false,authToken);
      case JOIN_PLAYER -> join(new Gson().fromJson(msg, JoinPlayer.class),session);
    }
  }

  public void join(JoinPlayer joinPlayer, Session session) throws IOException {
    //call the api to verify game in db
    try {
      //add an if statement here to return an error if game doesn't exist
      AuthData authData = authDao.getToken(joinPlayer.getAuthToken());
      String username = authData.getUsername();
      Integer gameID=joinPlayer.getGameID();
      ChessGame.TeamColor teamColor=joinPlayer.getPlayerColor();
      String authToken=joinPlayer.getAuthToken();

      //get game
      GameData gameData = gameDao.getGameData(gameID);
      ChessBoard game = gameData.getGame().getBoard();

      connectionManager.addSessionToGame(gameID, authToken, session);
      //send load game message use gson and session.getRemote().sendString(msg);
      LoadGame loadGame=new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game);
      session.getRemote().sendString(new Gson().toJson(loadGame));

      //session
      var message=String.format("%s has join the game as %s", username, teamColor);
      var notification=new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
      //exclude the client
      connectionManager.broadcastMessage(gameID,message,authToken);
    }
    catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }




}
