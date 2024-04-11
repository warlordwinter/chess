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
}
