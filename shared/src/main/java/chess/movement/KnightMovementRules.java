package chess.movement;

import chess.*;

import java.util.Collection;

public class KnightMovementRules extends ChessMovementRule{

  @Override
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> validMoves) {
    knightPredictions(chessBoard,chessPosition,validMoves,1,2); //Column Moves
    knightPredictions(chessBoard,chessPosition,validMoves,-1,2);
    knightPredictions(chessBoard,chessPosition,validMoves,2,1);
    knightPredictions(chessBoard,chessPosition,validMoves,-2,-1);
    knightPredictions(chessBoard,chessPosition,validMoves,-2,1); //Row Moves
    knightPredictions(chessBoard,chessPosition,validMoves,-1,-2);
    knightPredictions(chessBoard,chessPosition,validMoves,2,-1); //Row Moves
    knightPredictions(chessBoard,chessPosition,validMoves,1,-2);

  }
  public void knightPredictions(ChessBoard chessBoard, ChessPosition chessPosition, Collection <ChessMove> collection,int deltaRow, int deltaCol){

    int col = chessPosition.getColumn();
    int row = chessPosition.getRow();
    ChessPiece startingPiece = chessBoard.getPiece(chessPosition);
    ChessGame.TeamColor startingColor = startingPiece.getTeamColor();
    ChessPosition endingPosition;
    ChessPiece piece;
    boolean loopRestrictor = false;

    while (row >= 1 && row <= 8 && col >= 1 && col <= 8 && loopRestrictor ==false){
      loopRestrictor =true;
      row += deltaRow;
      col += deltaCol;
      if(row <=0||col<=0||row == 9||col==9)
        break;
      endingPosition=new ChessPosition(row, col);
      if (endingPosition.getRow() >=9 ||endingPosition.getColumn() >=9||endingPosition.getRow()<=0 ||endingPosition.getColumn()<=0){
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
