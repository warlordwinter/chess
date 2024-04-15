package webSocketMessages.userCommands.commands;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class MakeMove extends UserGameCommand {
  private Integer gameID;
  private ChessMove move;

  public MakeMove(String authToken, Integer gameID, ChessMove move) { // Renamed parameter from commandType to moveCommandType
    super(authToken);
    this.gameID = gameID;
    this.move = move;
    this.commandType = CommandType.MAKE_MOVE;
  }
  @Override
  public CommandType getCommandType() {
    return commandType;
  }

  public Integer getGameId() {
    return gameID;
  }

  public ChessMove getChessMove() {
    return move;
  }
}
