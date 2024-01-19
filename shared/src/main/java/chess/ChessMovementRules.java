package chess;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
//import java.util.List;

/**
 * This is the parent class that has some general attributes that apply to all the pieces
 */
public class ChessMovementRules {
    protected int[][] chessMovesArray;
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition){
    System.out.println(chessPosition);
  }
}
/**
 * This is the Pawn class that has all the movement rules for the Pawn
 */
class Pawn extends ChessMovementRules{

}



/**
 * This is the King class that has all the movement rules for the King
 */
class King extends ChessMovementRules{

}
/**
 * This is the Bishop class that has all the movement rules for the Bishop
 */
class Bishop extends ChessMovementRules{
  @Override
  public void chessMove(ChessBoard chessBoard,ChessPosition chessPosition){
    //take chess position and determine objects around it.
    //iterate through the array and find all the coordinates for possible movement spots.
    int currentColumn = chessPosition.getColumn();
    int currentRow =chessPosition.getRow();
    ChessPiece[][] squares = ChessBoard.getSquares();
    for (int i = 0; i <9; i++){
      for(int j = 0; j <9; j++){
        if(squares[i][j] == null){
          int[] coordinates = {i,j};

          //turn this function into the parent class
          //record where all the pieces are.
          //check to see if the piece is one that we are allowed to take
          //if so add it to the collection
          //cross-reference the pieces to see if the pieces in the way we are allowed to take
          //return array points.
        }
      }
    }
  }

}
/**
 * This is the Rook class that has all the movement rules for the Rook
 */
class Rook extends ChessMovementRules{

}
/**
 * This is the Queen class that has all the movement rules for the Queen
 */
class Queen extends ChessMovementRules{

}

/**
 * This is the Knight class that has all the movement rules for the Knight
 */
class Knight extends ChessMovementRules{

}