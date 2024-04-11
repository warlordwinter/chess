package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserver extends UserGameCommand {
  private String observerAuthToken; // Renamed from 'authToken'
  private Integer gameID;
  private UserGameCommand userGameCommand;

  public JoinObserver(String observerAuthToken, Integer gameID, UserGameCommand userGameCommand) {
    super(observerAuthToken);
    this.gameID = gameID;
    this.userGameCommand = userGameCommand;
  }

  public String getObserverAuthToken() {
    return observerAuthToken;
  }

  public Integer getGameID() {
    return gameID;
  }
}
