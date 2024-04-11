package server.websocket;

import com.google.gson.Gson;
import dataAccess.DatabaseManager;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.UserGameCommand;

import java.sql.Connection;

import static dataAccess.DatabaseManager.getConnection;

@WebSocket
public class WebSocketHandler {

  @OnWebSocketConnect
  public void onConnect(Session session){
    System.out.println("New WebSocket Connection Established: " + session.getLocalAddress());
  }

  @OnWebSocketMessage
  public void onMessage(Session session, String msg) throws Exception {
    UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);

    String authToken = command.getAuthString();

    Connection conn = DatabaseManager.getConnection();
    switch(command.getCommandType()){
      case JOIN_PLAYER -> join(conn,msg);
    }
  }

  private void join(Connection conn, msg);

}
