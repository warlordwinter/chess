package webSocketMessages.serverMessages.messages;

import webSocketMessages.serverMessages.ServerMessage;

public class Error extends ServerMessage {
  private String errorMessage;
  public Error(String errorMessage) {
    super(ServerMessageType.ERROR);
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

}
