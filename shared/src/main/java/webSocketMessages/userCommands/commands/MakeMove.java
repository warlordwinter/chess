package webSocketMessages.userCommands.commands;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class MakeMove extends UserGameCommand {
  private String auth_token;
  private Integer gameID;
  private CommandType commandType;
  private ChessMove chessMove;
  public MakeMove(String authToken, Integer gameID, CommandType commandType, ChessMove chessMove) {
    super(authToken);
    this.gameID = gameID;
    this.commandType = commandType;
    this.chessMove = chessMove;
  }
}
