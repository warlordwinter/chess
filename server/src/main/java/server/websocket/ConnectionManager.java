package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

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
}
