package ui.websocket;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import exception.ResponseException;
import ui.ChessBoardUI;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.messages.Error;
import webSocketMessages.serverMessages.messages.LoadGame;
import webSocketMessages.serverMessages.messages.Notification;
import webSocketMessages.userCommands.commands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {


  Session session;
  ChessBoard currentBoard;
  NotificationHandler notificationHandler;
  static String[] headers = {"a","b","c","d","e","f","g","h"};
  static String[] columns = {"1","2","3","4","5","6","7","8"};
  ChessGame.TeamColor playerBoardColor;

  public ChessGame.TeamColor getPlayerBoardColor() {
    return playerBoardColor;
  }

  public WebSocketFacade(String serverUrl, NotificationHandler notificationHandler) throws ResponseException {
    try {
      serverUrl=serverUrl.replace("http", "ws");
      URI socketURI=new URI(serverUrl + "/connect");
      this.notificationHandler=notificationHandler;
      System.out.println("Connected on WS Port "+ socketURI);

      WebSocketContainer container =ContainerProvider.getWebSocketContainer();
      this.session =(Session) container.connectToServer(this,socketURI);
      //deserialize the server message similar to websockethandler



      this.session.addMessageHandler(new MessageHandler.Whole<String>() {
        @Override
        public void onMessage(String message) {
          ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);

          switch(notification.getServerMessageType()){
            case ERROR -> error(message);
            case NOTIFICATION -> notification(message);
            case LOAD_GAME -> loadGame(message);
            default -> System.out.println("Something Weird Happened");
          }

//          notificationHandler.notify(notification);
        }
      });



    } catch (DeploymentException | IOException | URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }
  public void notification(String message) {
    Notification notifications = new Gson().fromJson(message, Notification.class);

    System.out.println(notifications.getMessage());
  }

  public void loadGame(String message) {
    LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
    if(loadGame.getGame()!=null){
      System.out.println("Game is Loading");
    }else{
      System.out.println("Error: Something is wrong with the board");
    }

    ChessBoardUI.buildBoard(loadGame.getGame(),false,headers,columns,playerBoardColor,null);
    currentBoard = loadGame.getGame();
  }

  public void error(String message) {
    Error error = new Gson().fromJson(message, Error.class);

    System.out.println(error.getErrorMessage());
  }

  public void joinGame(String authToken, int gameID, ChessGame.TeamColor playerColor){
    try {
      JoinPlayer joinPlayer=new JoinPlayer(authToken, gameID, playerColor);
      playerBoardColor =playerColor;
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
      this.session.getBasicRemote().sendText(new Gson().toJson(makeMove));
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

  public ChessBoard getCurrentBoard() {
    return currentBoard;
  }

  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }

  public void setCurrentBoard(ChessBoard currentBoard) {
    this.currentBoard=currentBoard;
  }
}
