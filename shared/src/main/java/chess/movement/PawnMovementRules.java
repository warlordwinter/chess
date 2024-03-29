package chess.movement;

import chess.*;

import javax.swing.text.Position;
import java.util.Collection;

public class PawnMovementRules extends ChessMovementRule {

  private boolean isOnBoard(ChessBoard board, ChessPosition position){
    if(position.getColumn() >=9||position.getColumn()<=0){
      return false;
    }
    return true;

  }
  @Override
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> collection) {
    pawnMoves(chessBoard, chessPosition, collection);
  }

  private void promotionPotential(ChessPosition endingPosition, ChessPosition chessPosition, Collection<ChessMove> collection) {
    int col=endingPosition.getColumn();
    int row=endingPosition.getRow();
    if (row == 8 || row == 1 || col == 8 || col == 0) {
      collection.add(new ChessMove(chessPosition, endingPosition, ChessPiece.PieceType.QUEEN));
      collection.add(new ChessMove(chessPosition, endingPosition, ChessPiece.PieceType.ROOK));
      collection.add(new ChessMove(chessPosition, endingPosition, ChessPiece.PieceType.KNIGHT));
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
    if (row == 2&& startingColor == ChessGame.TeamColor.WHITE||row ==7 &&startingColor== ChessGame.TeamColor.BLACK) { //special start moves
      if(startingColor == ChessGame.TeamColor.WHITE){
        endingPosition = new ChessPosition(row +1,col);
        if (chessBoard.getPiece(endingPosition) ==null){
          collection.add(new ChessMove(chessPosition, endingPosition, null));
          endingPosition = new ChessPosition(row+2,col);
          if (chessBoard.getPiece(endingPosition) ==null){
            endingPosition = new ChessPosition(row+2,col);
            collection.add(new ChessMove(chessPosition, endingPosition, null));
          }
        }
      }
      if(startingColor == ChessGame.TeamColor.BLACK){
        endingPosition = new ChessPosition(row -1,col);
        if (chessBoard.getPiece(endingPosition) ==null){
          collection.add(new ChessMove(chessPosition, endingPosition, null));
          endingPosition = new ChessPosition(row-2,col);
          if (chessBoard.getPiece(endingPosition) ==null){
            endingPosition = new ChessPosition(row-2,col);
            collection.add(new ChessMove(chessPosition, endingPosition, null));
          }
        }
      }
    }
      int i =1;
      endingPosition=new ChessPosition(row + i *increment, col); // advance regularly on non-starter moves
      if (chessBoard.getPiece(endingPosition) == null) {
        promotionPotential(endingPosition, chessPosition, collection);//check to promote regularly
      }

      row = chessPosition.getRow();
    //next side to side killing
    endingPosition=new ChessPosition(row + increment, col + 1);//left
    if(endingPosition.getColumn()==9){
      col=8;
      endingPosition = new ChessPosition(row+increment,col);
    }
    ChessPiece left = chessBoard.getPiece(endingPosition);
    if (left !=null && startingColor != chessBoard.getPiece(endingPosition).getTeamColor()) {
      promotionPotential(endingPosition, chessPosition, collection);
    }
    endingPosition=new ChessPosition(row + increment, col - 1); //right
    if(isOnBoard(chessBoard,endingPosition) == true){
    ChessPiece right = chessBoard.getPiece(endingPosition);
      if (right!=null &&startingColor != chessBoard.getPiece(endingPosition).getTeamColor()) {
        promotionPotential(endingPosition, chessPosition, collection);
      }
    }
  }
}
