package webSocketMessages.serverMessages.messages;

import chess.ChessBoard;
import webSocketMessages.serverMessages.ServerMessage;

public class LoadGame extends ServerMessage {
  private ServerMessageType serverMessageType;
  private ChessBoard game;
  public LoadGame(ServerMessageType type,ChessBoard game) {
    super(type);
    this.game = game;
  }

}
