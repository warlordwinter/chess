package webSocketMessages.userCommands.commands;

import webSocketMessages.userCommands.UserGameCommand;

public class Leave extends UserGameCommand {
  private String authToken1;
  private Integer gameID;

  public Leave(String authToken1, Integer gameID) {
    super(authToken1);
    this.gameID = gameID;
    this.commandType = CommandType.LEAVE;
  }


  public Integer getGameID() {
    return gameID;
  }
  @Override
  public CommandType getCommandType() {
    return commandType;
  }

}
