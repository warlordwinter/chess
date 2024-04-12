package ui.websocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import exception.ResponseException;
import webSocketMessages.userCommands.commands.*;

import javax.management.Notification;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {


  Session session;
  NotificationHandler notificationHandeler;


  public WebSocketFacade(String serverUrl, NotificationHandler notificationHandler) throws ResponseException {
    try {
      serverUrl=serverUrl.replace("http", "ws");
      URI socketURI=new URI(serverUrl + "/connect");
      this.notificationHandeler=notificationHandler;

      WebSocketContainer container =ContainerProvider.getWebSocketContainer();
      this.session =(Session) container.connectToServer(this,socketURI);
      //deserialize the server message similar to websockethandler

      this.session.addMessageHandler(new MessageHandler.Whole<String>() {
        @Override
        public void onMessage(String message) {
          Notification notification = new Gson().fromJson(message, Notification.class);
          notificationHandler.notify(notification);
        }
      });



    } catch (DeploymentException | IOException | URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void joinGame(String authToken, int gameID, ChessGame.TeamColor playerColor){
    try {
      JoinPlayer joinPlayer=new JoinPlayer(authToken, gameID, playerColor);
      this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
    }catch(IOException e){
      throw new RuntimeException(e.getMessage());

    }
  }

  public void leave(String authToken, int gameID){
    try {
      Leave leave=new Leave(authToken, gameID);
      this.session.getBasicRemote().sendText(new Gson().toJson(leave));
    }catch(IOException e){
      throw new RuntimeException(e.getMessage());

    }
  }

  public void makeMove(String authToken, Integer gameID, ChessMove move){
    try {
      MakeMove makeMove = new MakeMove(authToken,gameID,move);
      this.session.getBasicRemote().sendText(new Gson().toJson(move));
    }catch(IOException e){
      throw new RuntimeException(e.getMessage());
    }
  }

  public void resign(String authToken, int gameID){
    try {
      Resign resign=new Resign(authToken, gameID);
      this.session.getBasicRemote().sendText(new Gson().toJson(resign));
    }catch(IOException e){
      throw new RuntimeException(e.getMessage());

    }
  }

  public void joinObserver(String authToken, int gameID){
    try {
      JoinObserver observer = new JoinObserver(authToken, gameID);
      this.session.getBasicRemote().sendText(new Gson().toJson(observer));
    }catch(IOException e){
      throw new RuntimeException(e.getMessage());

    }
  }

  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {

  }
}
