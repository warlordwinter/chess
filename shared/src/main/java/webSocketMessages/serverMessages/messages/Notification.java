package webSocketMessages.serverMessages.messages;

import webSocketMessages.serverMessages.ServerMessage;

public class Notification extends ServerMessage {
  private String message;
  public Notification(String message) {
    super(ServerMessageType.NOTIFICATION);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
