package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class Leave extends UserGameCommand {
  private String authToken;
  private Integer gameID;

  public Leave(String authToken, Integer gameID) {
    super(authToken);
    this.gameID = gameID;
  }


  public Integer getGameID() {
    return gameID;
  }
}
