package webSocketMessages.userCommands.commands;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class JoinPlayer extends UserGameCommand {
  private String authToken;
  private Integer gameID;
  private ChessGame.TeamColor playerColor;
  private CommandType commandType;
  public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor playerColor,CommandType commandType) {
    super(authToken);
    this.gameID = gameID;
    this.playerColor = playerColor;
    this.commandType = commandType;
  }

  public String getAuthToken() {
    return authToken;
  }

  public Integer getGameID() {
    return gameID;
  }

  public ChessGame.TeamColor getPlayerColor() {
    return playerColor;
  }

  @Override
  public CommandType getCommandType() {
    return commandType;
  }
}
