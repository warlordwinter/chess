package webSocketMessages.serverMessages.messages;

import webSocketMessages.serverMessages.ServerMessage;

public class Error extends ServerMessage {
  private String serverMessage;
  private ServerMessageType serverMessageType;
  public Error(ServerMessageType type, String serverMessage) {
    super(type);
    this.serverMessage = serverMessage;
  }
}
