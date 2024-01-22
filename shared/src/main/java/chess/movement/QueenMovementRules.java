package chess.movement;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class QueenMovementRules extends ChessMovementRule{
  @Override
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> validMoves) {
    preditions(chessBoard,chessPosition,0,-1,validMoves); // Rook Moves
    preditions(chessBoard,chessPosition,1,0,validMoves);
    preditions(chessBoard,chessPosition,0,1,validMoves);
    preditions(chessBoard,chessPosition,-1,0,validMoves);
    preditions(chessBoard,chessPosition,1,1,validMoves); //Bishop Moves
    preditions(chessBoard,chessPosition,-1,1,validMoves);
    preditions(chessBoard,chessPosition,-1,-1,validMoves);
    preditions(chessBoard,chessPosition,1,-1,validMoves);
  }
}

