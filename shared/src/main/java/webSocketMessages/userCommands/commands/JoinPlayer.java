package webSocketMessages.userCommands.commands;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class JoinPlayer extends UserGameCommand {
  private String joinPlayerAuthToken; // No @SerializedName annotation

  private Integer gameID;
  private ChessGame.TeamColor playerColor;


  public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor playerColor) {
    super(authToken);
    this.gameID = gameID;
    this.playerColor = playerColor;

  }

  public String getAuthToken() {
    return joinPlayerAuthToken;
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

