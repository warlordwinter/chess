package webSocketMessages.serverMessages.messages;

import webSocketMessages.serverMessages.ServerMessage;

public class Notification extends ServerMessage {
  private String message;
  private ServerMessageType serverMessageType;
  public Notification(ServerMessageType type, String message) {
    super(type);
    this.message = message;
  }
}
