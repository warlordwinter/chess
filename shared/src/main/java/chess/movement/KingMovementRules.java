package chess.movement;

import chess.*;

import javax.swing.text.Position;
import java.util.Collection;

public class KingMovementRules extends ChessMovementRule{

//  public void kingPredictions(chessBoard, chessPosition)
  @Override
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> validMoves) {
    kingPredictions(chessBoard,chessPosition,validMoves,0,1);
    kingPredictions(chessBoard,chessPosition,validMoves,1,0);
    kingPredictions(chessBoard,chessPosition,validMoves,0,-1);
    kingPredictions(chessBoard,chessPosition,validMoves,-1,0);
    kingPredictions(chessBoard,chessPosition,validMoves,1,1);
    kingPredictions(chessBoard,chessPosition,validMoves,-1,1);
    kingPredictions(chessBoard,chessPosition,validMoves,1,-1);
    kingPredictions(chessBoard,chessPosition,validMoves,-1,-1);

  }
  public void kingPredictions(ChessBoard chessBoard, ChessPosition chessPosition, Collection <ChessMove> collection,int deltaRow, int deltaCol){

    int col = chessPosition.getColumn();
    int row = chessPosition.getRow();
    ChessPiece startingPiece = chessBoard.getPiece(chessPosition);
    ChessGame.TeamColor startingColor = startingPiece.getTeamColor();
    ChessPosition endingPosition;
    ChessPiece piece;
    boolean loopRestrictor = false;

    //check kings current position
    //check all adjacent spots. check for enemy pieces
    //add to collection
    //return collection
    while (row >= 1 && row <= 8 && col >= 1 && col <= 8 && loopRestrictor ==false){
      loopRestrictor =true;
      row += deltaRow;
      col += deltaCol;
      if(row ==0||col==0||row == 9||col==9)
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