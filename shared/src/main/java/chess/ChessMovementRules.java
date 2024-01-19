package chess;

import java.util.ArrayList;
//import java.util.List;

/**
 * This is the parent class that has some general attributes that apply to all the pieces
 */
public class ChessMovementRules {
    protected int[][] chessMovesArray;

  /**
   * This is the overarching method for all the chess peices to know where they can move.
   * @param chessBoard
   * @param chessPosition
   */
    public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition){
    System.out.println(chessPosition);
  }

  /**
   * This function allows the program to know all the possible empty spaces on the board.
   * We can then use this with the indivigual movement rules for each class to be able to move accordingly.
   *
   * @param chessBoard
   * @return emptyChessSpacesArray
   */
  public int[][] calculateCurrentBoard(ChessBoard chessBoard){
    ChessPiece[][] squares = ChessBoard.getSquares();
    ArrayList<int[]> emptyChessSpaces = new ArrayList<>();
    for (int i = 0; i <9; i++){
      for(int j = 0; j <9; j++){
        if(squares[i][j] == null){
          int[] coordinates = {i,j};
          emptyChessSpaces.add(coordinates);
          //turn this function into the parent class
          //record where all the pieces are.
          //check to see if the piece is one that we are allowed to take
          //if so add it to the collection
          //cross-reference the pieces to see if the pieces in the way we are allowed to take
          //return array points.
        }
      }
    }

    int[][] emptyChessSpacesArray = new int[emptyChessSpaces.size()][2];
    for (int i = 0; i < emptyChessSpaces.size(); i++){
      emptyChessSpacesArray[i] = emptyChessSpaces.get(i);
    }
    return emptyChessSpacesArray;
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
    int[][] possibleChessBoardMoves = calculateCurrentBoard(chessBoard);
    //implement an array for all the coordinates the Bishop can go to.
    int col = chessPosition.getColumn();
    int row = chessPosition.getRow();

    for (int i = 0; row<=8; i++){
      for(int j =0; col<=8;j++){

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
}
