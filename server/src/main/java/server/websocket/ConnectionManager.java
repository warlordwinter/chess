package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
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

//  public void broadcastMessage(Integer gameID, String msg, String authToken){
//    Map<String, Session> gameSessions = sessions.get(gameID);
//    if (gameSessions != null) {
//      for (Map.Entry<String, Session> entry : gameSessions.entrySet()) {
//        String currentAuthToken = entry.getKey();
//        Session currentSession = entry.getValue();
//        if (!currentAuthToken.equals(authToken)) {
//          try {
//            currentSession.getRemote().sendString(new Gson().toJson(msg));
//          } catch (Exception e) {
//            // Handle the exception, e.g., log it
//            System.err.println("Failed to send message to session: " + e.getMessage());
//          }
//        }
//      }
//    }
//  }
public void broadcast(Integer gameID, Notification notification, String exceptThisAuthToken) {
  Map<String, Session> gameSessions = sessions.get(gameID);
  if (gameSessions != null) {
    for (Map.Entry<String, Session> entry : gameSessions.entrySet()) {
      String authToken = entry.getKey();
      Session session = entry.getValue();
      if (!authToken.equals(exceptThisAuthToken)) {
        try {
          session.getRemote().sendString(new Gson().toJson(notification));
        } catch (IOException e) {
          // Handle the exception, e.g., log it
          System.err.println("Failed to send notification to session: " + e.getMessage());
        }
      }
    }
  }
}


}
