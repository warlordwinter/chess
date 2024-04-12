package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class Leave extends UserGameCommand {
  private String auth_token;
  private Integer gameID;
  private UserGameCommand commandType;
  public Leave(String authToken, Integer gameID,UserGameCommand commandType) {
    super(authToken);
    this.gameID = gameID;
    this.commandType = commandType;
  }
}
