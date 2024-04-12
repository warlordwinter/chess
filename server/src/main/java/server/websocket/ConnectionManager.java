package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.messages.Error;
import webSocketMessages.serverMessages.messages.LoadGame;
import webSocketMessages.serverMessages.messages.Notification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
  public final Map<Integer, Map<String, Session>> sessions = new HashMap<>();

  void addSessionToGame(Integer gameID,String authToken, Session session){
    if (!sessions.containsKey(gameID)){
      sessions.put(gameID, new HashMap<>());
    }
    Map<String,Session> gameSessions =sessions.get(gameID);
    gameSessions.put(authToken,session);
  }

  void removeSessionFromGame(Integer gameID,String authToken, Session session){

    if(!sessions.containsKey(gameID)){
      Map<String, Session> gameSessions = sessions.get(gameID);
      gameSessions.remove(authToken);
    }else{
      System.out.println("GameID doesn't exist");
    }
  }

  Map<String,Session> getSessionsFOrGame(Integer gameID){return sessions.get(gameID);}

public void broadcast(Integer gameID, Notification notification, String exceptThisAuthToken, boolean sendToAll) throws IOException {
  Map<String, Session> gameSessions = sessions.get(gameID);
  if (gameSessions != null) {
    String notify = new Gson().toJson(notification);
    for (Map.Entry<String, Session> entry : gameSessions.entrySet()) {
      String authToken = entry.getKey();
      Session session = entry.getValue();
      if (!sendToAll && authToken.equals(exceptThisAuthToken)) {
        continue; // Skip this session if it matches exceptThisAuthToken and sendToAll is false
      }
      try {
        session.getRemote().sendString(notify);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

  public void broadcastError(Integer gameID, Error error, String authToken) throws IOException {
    Map<String, Session> gameSessions = sessions.get(gameID);
    if (gameSessions != null) {
      String notify = new Gson().toJson(error);
      Session session = gameSessions.get(authToken);
      if (session != null) {
        try {
          session.getRemote().sendString(notify);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } else {
        System.err.println("Session with authToken " + authToken + " not found.");
      }
    } else {
      System.err.println("Game with ID " + gameID + " not found.");
    }
  }




  public void broadcastGame(Integer gameID, LoadGame loadGame) {
    Map<String, Session> gameSessions = sessions.get(gameID);
    if (gameSessions != null) {
      String loadGameJson = new Gson().toJson(loadGame);
      for (Map.Entry<String, Session> entry : gameSessions.entrySet()) {
        Session session = entry.getValue();
        try {
          session.getRemote().sendString(loadGameJson);
        } catch (IOException e) {
          // Handle the exception, e.g., log it
          System.err.println("Failed to send LoadGame message to session: " + e.getMessage());
        }
      }
    }
  }

}
