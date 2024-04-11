package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class Resign extends UserGameCommand {
  private String auth_token;
  private Integer gameID;

  private CommandType commandType;
  public Resign(String authToken, Integer gameID,CommandType commandType) {
    super(authToken);
    this.gameID = gameID;
    this.commandType = commandType;
  }
}
