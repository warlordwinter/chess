package chess.movement;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class BishopMovementRules extends ChessMovementRules{
  @Override
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> validMoves) {
    preditions(chessBoard,chessPosition,1,1,validMoves);
    preditions(chessBoard,chessPosition,-1,1,validMoves);
    preditions(chessBoard,chessPosition,-1,-1,validMoves);
    preditions(chessBoard,chessPosition,1,-1,validMoves);
  }
}
