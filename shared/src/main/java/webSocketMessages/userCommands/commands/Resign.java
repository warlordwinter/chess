package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class Resign extends UserGameCommand {
  private String authToken1;
  private Integer gameID;

  public Resign(String authToken1, Integer gameID) {
    super(authToken1);
    this.gameID = gameID;
    this.commandType = CommandType.RESIGN;
  }
  @Override
  public CommandType getCommandType() {
    return commandType;
  }

  public Integer getGameID() {
    return gameID;
  }

}
