package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class Resign extends UserGameCommand {
  private String auth_token;
  private Integer gameID;

  public Resign(String authToken, Integer gameID) {
    super(authToken);
    this.gameID = gameID;
  }

  public Integer getGameID() {
    return gameID;
  }

  public String getAuth_token() {
    return auth_token;
  }
}
