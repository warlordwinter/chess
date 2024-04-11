package webSocketMessages.serverMessages.messages;

import chess.ChessBoard;
import webSocketMessages.serverMessages.ServerMessage;

public class LoadGame extends ServerMessage {
  private ServerMessageType serverMessageType;
  private ChessBoard chessBoard;
  public LoadGame(ServerMessageType type,ChessBoard chessBoard) {
    super(type);
    this.chessBoard = chessBoard;
  }
}
