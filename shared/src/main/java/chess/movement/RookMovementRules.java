package chess.movement;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class RookMovementRules extends ChessMovementRule{


  @Override
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> validMoves) {
    preditions(chessBoard,chessPosition,1,0,validMoves);
    preditions(chessBoard,chessPosition,0,1,validMoves);
    preditions(chessBoard,chessPosition,-1,0,validMoves);
    preditions(chessBoard,chessPosition,0,-1,validMoves);
  }

}
