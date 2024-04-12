package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class Leave extends UserGameCommand {
  private String auth_token;
  private Integer gameID;

  public Leave(String authToken, Integer gameID) {
    super(authToken);
    this.gameID = gameID;
  }

  public String getAuth_token() {
    return auth_token;
  }

  public Integer getGameID() {
    return gameID;
  }
}
