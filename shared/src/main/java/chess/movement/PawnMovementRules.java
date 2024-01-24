package chess.movement;

import chess.*;

import java.util.Collection;

public class PawnMovementRules extends ChessMovementRule {

  @Override
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> validMoves) {

  }
//  public void killing(ChessPosition chessPosition, int deltaCol, int deltaRow){
//
//  }
  public void pawnMovement(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> collection) {
    int col=chessPosition.getColumn();
    int row=chessPosition.getRow();
    boolean firstMoveAvaliable=true;
    ChessPosition endingPosition;
    ChessPiece piece;
    ChessPiece startingPiece=chessBoard.getPiece(chessPosition);
    ChessGame.TeamColor startingColor=startingPiece.getTeamColor();

    if (startingColor == ChessGame.TeamColor.WHITE) {
      int increment = 1;
      if (firstMoveAvaliable=true) { //special start moves
        //if the teamcolor is BLACK set i to -1
        for (int i=0; i <= 2 || i >= -2; i++) {
          endingPosition=new ChessPosition(row, col);
          if (chessBoard.getPiece(endingPosition) == null) {
            collection.add(new ChessMove(chessPosition, endingPosition, null));
          }
        }
        endingPosition=new ChessPosition(row, col); // advance regularly on non-starter moves
        if (chessBoard.getPiece(endingPosition) == null) {
          collection.add(new ChessMove(chessPosition, endingPosition, null));
        }
      }
      //next side to side killing
      endingPosition=new ChessPosition(row - increment, col + 1); //left
      if (startingColor != chessBoard.getPiece(endingPosition).getTeamColor() ) {
        collection.add(new ChessMove(chessPosition, endingPosition, null));
      }
      endingPosition=new ChessPosition(row + increment, col - 1); //right
      if (startingColor != chessBoard.getPiece(endingPosition).getTeamColor() ) {
        collection.add(new ChessMove(chessPosition, endingPosition, null));
      }

    }
  }
}