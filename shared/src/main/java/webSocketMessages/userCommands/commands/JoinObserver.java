package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserver extends UserGameCommand {
  private String observerAuthToken; // Renamed from 'authToken'
  private Integer gameID;

  public JoinObserver(String observerAuthToken, Integer gameID) {
    super(observerAuthToken);
    this.gameID = gameID;
  }

  public String getObserverAuthToken() {
    return observerAuthToken;
  }

  public Integer getGameID() {
    return gameID;
  }
}
