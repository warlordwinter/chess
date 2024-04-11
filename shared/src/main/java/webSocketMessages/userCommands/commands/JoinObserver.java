package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserver extends UserGameCommand {
  private String authToken;
  private Integer gameID;
  private UserGameCommand userGameCommand;

  public JoinObserver(String authToken, Integer gameID,UserGameCommand userGameCommand) {
    super(authToken);
    this.gameID = gameID;
    this.userGameCommand = userGameCommand;
  }

  public String getAuthToken() {
    return authToken;
  }
  public Integer getGameID() {
    return gameID;
  }
}
