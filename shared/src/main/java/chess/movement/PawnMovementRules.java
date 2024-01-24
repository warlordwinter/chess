package chess.movement;

import chess.*;

import java.util.Collection;

public class PawnMovementRules extends ChessMovementRule {

  @Override
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> collection) {
    pawnMoves(chessBoard, chessPosition, collection);
  }

  //  public void killing(ChessPosition chessPosition, int deltaCol, int deltaRow){
//
//  }
  private void promotionPotential(ChessPosition endingPosition, ChessPosition chessPosition, Collection<ChessMove> collection) {
    int col=endingPosition.getColumn();
    int row=endingPosition.getRow();
    if (row == 8 || row == 1 || col == 8 || col == 0) {
      collection.add(new ChessMove(chessPosition, endingPosition, ChessPiece.PieceType.KNIGHT));
      collection.add(new ChessMove(chessPosition, endingPosition, ChessPiece.PieceType.QUEEN));
      collection.add(new ChessMove(chessPosition, endingPosition, ChessPiece.PieceType.ROOK));
      collection.add(new ChessMove(chessPosition, endingPosition, ChessPiece.PieceType.BISHOP));
    } else {
      collection.add(new ChessMove(chessPosition, endingPosition, null));
    }

  }

  public void pawnMoves(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> collection) {
    int col=chessPosition.getColumn();
    int row=chessPosition.getRow();
    boolean firstMoveAvaliable=true;
    ChessPosition endingPosition;
    ChessPiece piece;
    ChessPiece startingPiece=chessBoard.getPiece(chessPosition);
    ChessGame.TeamColor startingColor=startingPiece.getTeamColor();
    int increment;

    if (startingColor == ChessGame.TeamColor.WHITE) {
      increment=1;
    } else {
      increment=-1;//if the teamcolor is BLACK set i to -1
    }
    if (firstMoveAvaliable==true) { //special start moves
      for (int i=1; i <= 1 && i >= -1; i+=increment) {
        endingPosition=new ChessPosition(row + i * increment, col);
        if (chessBoard.getPiece(endingPosition) != null){
          break;
        }
        else {
          collection.add(new ChessMove(chessPosition, endingPosition, null));
        }
      }
      endingPosition=new ChessPosition(row, col); // advance regularly on non-starter moves
      if (chessBoard.getPiece(endingPosition) == null) {
        promotionPotential(endingPosition, chessPosition, collection);//check to promote regularly
//            collection.add(new ChessMove(chessPosition,endingPosition, ChessPiece.PieceType.KNIGHT));
//            collection.add(new ChessMove(chessPosition,endingPosition, ChessPiece.PieceType.BISHOP));
//            collection.add(new ChessMove(chessPosition,endingPosition, ChessPiece.PieceType.QUEEN));
//            collection.add(new ChessMove(chessPosition,endingPosition, ChessPiece.PieceType.ROOK));
//          }else{
//          collection.add(new ChessMove(chessPosition, endingPosition, null));
//          }
      }
    }
    //next side to side killing
    endingPosition=new ChessPosition(row - increment, col + 1); //left
    ChessPiece left = chessBoard.getPiece(endingPosition);
    if (left !=null && startingColor != chessBoard.getPiece(endingPosition).getTeamColor()) {
      collection.add(new ChessMove(chessPosition, endingPosition, null));
    }
    endingPosition=new ChessPosition(row + increment, col - 1); //right
    ChessPiece right = chessBoard.getPiece(endingPosition);
    if (right!=null &&startingColor != chessBoard.getPiece(endingPosition).getTeamColor()) {
      collection.add(new ChessMove(chessPosition, endingPosition, null));
    }
  }
}
