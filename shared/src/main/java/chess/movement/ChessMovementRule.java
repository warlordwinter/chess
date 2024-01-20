package chess.movement;

import chess.*;

import java.util.Collection;

/**
 * This is the parent class that has some general attributes that apply to all the pieces
 */
public abstract class ChessMovementRule {
  /**
   * This is the overarching method for all the chess peices to know where they can move.
   * @param chessBoard
   * @param chessPosition
   */
  public abstract void chessMove(ChessBoard chessBoard, ChessPosition chessPosition,Collection<ChessMove> validMoves);

  protected void preditions(ChessBoard chessBoard,ChessPosition chessPosition,int deltaRow, int deltaCol,Collection <ChessMove> collection) {
    int col=chessPosition.getColumn();
    int row=chessPosition.getRow();
    ChessPiece startingPiece=chessBoard.getPiece(chessPosition);
    ChessGame.TeamColor startingColor=startingPiece.getTeamColor();

    while (row > 0 && row < 9 && col > 0 && col < 9) {
      ChessPosition endingPosition=new ChessPosition(row, col);
      ChessPiece piece=chessBoard.getPiece(endingPosition);
      if (piece == null) {
        collection.add(new ChessMove(chessPosition, endingPosition, null));
        row+=deltaRow;
        col+=deltaCol;
        endingPosition=new ChessPosition(row, col);
      } else {
        if (startingColor != piece.getTeamColor()) {
          collection.add(new ChessMove(chessPosition, endingPosition, null));
        }
        break;
      }

    }
  }
}