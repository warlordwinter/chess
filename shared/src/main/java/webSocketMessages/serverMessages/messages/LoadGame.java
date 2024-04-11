package webSocketMessages.serverMessages.messages;

import chess.ChessBoard;
import webSocketMessages.serverMessages.ServerMessage;

public class LoadGame extends ServerMessage {
  private ChessBoard game;
  public LoadGame(ChessBoard game) {
    super(ServerMessageType.LOAD_GAME);
    this.game = game;
  }

}
