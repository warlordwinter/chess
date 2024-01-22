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
//  protected void outOfBounds(Collection<ChessMove> validMoves){
//    for (){
//
//    }
  protected void preditions(ChessBoard chessBoard,ChessPosition chessPosition,int deltaRow, int deltaCol,Collection <ChessMove> collection) {
    int col=chessPosition.getColumn();
    int row=chessPosition.getRow();
    ChessPiece startingPiece=chessBoard.getPiece(chessPosition);
    ChessGame.TeamColor startingColor=startingPiece.getTeamColor();
    ChessPosition endingPosition;
    ChessPiece piece;
    boolean tokenRow =false;
    boolean tokenColumn = false;
    if (col==1) {
      col++;
      tokenColumn = true;
    }
    if (row==1){
      row++;
      tokenRow = true;
    }

    while (row > 1 && row < 8 && col > 1 && col < 8) {
      if (tokenRow == true||tokenColumn ==true){
        if(col ==2 ){
          col--;
          tokenColumn =false;
        } if(row ==2 ){
          row--;
          tokenRow = true;
        }
      }

      row+=deltaRow;
      col+=deltaCol;
      endingPosition=new ChessPosition(row, col);
      piece=chessBoard.getPiece(endingPosition);
//      if(endingPosition.getColumn() <= 0|| endingPosition.getRow()<=0  ||chessPosition.getColumn()>=8 ||chessPosition.getRow()>=8){
        if (piece == null) {
          if(row ==0){
            break;
          } else {
            collection.add(new ChessMove(chessPosition, endingPosition, null));
          }
        } else {
          if (startingColor != piece.getTeamColor()) {
            collection.add(new ChessMove(chessPosition, endingPosition, null));
          }
          break;
        }
      }
//    }
  }
}
