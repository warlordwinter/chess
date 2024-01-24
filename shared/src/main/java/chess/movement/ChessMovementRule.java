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
    ChessPosition endingPosition;
    ChessPiece piece;

    while (row >= 1 && row <= 8 && col >= 1 && col <= 8) {
      row+=deltaRow;
      col+=deltaCol;
      if(row <=0||col<=0||row >= 9||col>=9)
        break;
      endingPosition=new ChessPosition(row, col);
      if (endingPosition.getRow() >=9 ||endingPosition.getColumn() >=9){
        break;
      }
      piece=chessBoard.getPiece(endingPosition);
        if (piece == null) {
            collection.add(new ChessMove(chessPosition, endingPosition, null));
        } else {
          if (startingColor != piece.getTeamColor()) {
            collection.add(new ChessMove(chessPosition, endingPosition, null));
          }
          break;
        }
      }
  }
}
