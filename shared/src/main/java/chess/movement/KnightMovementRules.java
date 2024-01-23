package chess.movement;

import chess.*;

import java.util.Collection;

public class KnightMovementRules extends ChessMovementRule{

  @Override
  public void chessMove(ChessBoard chessBoard, ChessPosition chessPosition, Collection<ChessMove> validMoves) {
    preditions(chessBoard,chessPosition,1,2,validMoves); //Column Moves
    preditions(chessBoard,chessPosition,-1,2,validMoves);
    preditions(chessBoard,chessPosition,2,1,validMoves);
    preditions(chessBoard,chessPosition,-2,-1,validMoves);
    preditions(chessBoard,chessPosition,-2,1,validMoves); //Row Moves
    preditions(chessBoard,chessPosition,-1,-2,validMoves);
    preditions(chessBoard,chessPosition,2,-1,validMoves); //Row Moves
    preditions(chessBoard,chessPosition,1,-2,validMoves);

  }
//  public void knightPredictions(ChessBoard chessBoard, ChessPosition chessPosition, Collection <ChessMove> collection,int deltaCol, int deltaRow){
//    int col = chessPosition.getColumn();
//    int row = chessPosition.getRow();
//    ChessPiece startingPiece = chessBoard.getPiece(chessPosition);
//    ChessGame.TeamColor startingColor = startingPiece.getTeamColor();
//    ChessPosition endingPosition;
//    ChessPiece piece;
//
//    while (row >= 1 && row <= 8 && col >= 1 && col <= 8){
//      //predict a move and see if it's on the board
//      //if not on the board don't add
//      //if is on board check if friend or foe.
//
//      endingPosition= new ChessPosition(row,col);
//      if (row<=0||col<=0||row>=9||col>=9);
//    }
//  }
}
