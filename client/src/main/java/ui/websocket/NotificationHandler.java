package ui.websocket;

import webSocketMessages.serverMessages.ServerMessage;

public interface NotificationHandler {
  void notify(ServerMessage notification);


   // create three methods and would take in a notification deserialize this inside server facade

}