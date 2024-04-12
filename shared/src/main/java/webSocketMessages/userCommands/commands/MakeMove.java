package webSocketMessages.userCommands.commands;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class MakeMove extends UserGameCommand {
  private Integer gameID;
  private ChessMove move;
  private UserGameCommand userGameCommand;

  public MakeMove(String authToken, Integer gameID, ChessMove move,UserGameCommand userGameCommand) { // Renamed parameter from commandType to moveCommandType
    super(authToken);
    this.gameID = gameID;
    this.move = move;
    this.userGameCommand = userGameCommand;
  }

  public Integer getGameId() {
    return gameID;
  }

  public ChessMove getChessMove() {
    return move;
  }
}
