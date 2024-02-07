package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor team;
    private ChessBoard board;
    public ChessGame() {
        this.board = new ChessBoard();
        this.team = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // get the piece at that position
        // call the get moves
        // return it.
        ChessBoard board = getBoard();
        ChessPiece piece = board.getPiece(startPosition);
        if(piece ==null){
            return null;
        }else {
            //clone
            ChessBoard clone = new ChessBoard(board);
            ArrayList <ChessPosition> endingLocations = new ArrayList<>();
            ArrayList <ChessMove> inValidMoves = new ArrayList<>();
            ChessPiece clonePiece = clone.getPiece(startPosition);
            ChessPiece.PieceType clonePiecetype =  clonePiece.getPieceType();
            Collection<ChessMove> collection = clonePiece.pieceMoves(clone,startPosition);
//          collection.forEach(chessMove -> endingLocations.add(chessMove.getEndPosition()));// turn this into a for loop

            for (ChessMove chessMove : collection){
                ChessPosition endingPosition = chessMove.getEndPosition();
                clone.addPiece(startPosition, null);
                clone.addPiece(endingPosition,clonePiece);
                team = getTeamTurn();

                if(isInCheck(team) ==true){
                    inValidMoves.add(chessMove); // make chessMove a loop var
                }
                clone.addPiece(endingPosition, null);
                clone.addPiece(startPosition,clonePiece);

            }

//            for(int i =0; i<=endingLocations.size(); i++){
//                ChessPosition endingPosition = endingLocations.get(i);
//                clone.addPiece(startPosition, null);
//                clone.addPiece(endingPosition,clonePiece);
//                team = getTeamTurn();
//
//                if(isInCheck(team) ==true){
//                    inValidMoves.add(chessMove); // make chessMove a loop var
//                }
//                clone.addPiece(endingPosition, null);
//                clone.addPiece(startPosition,clonePiece);
//            }
            collection.removeAll(inValidMoves);
            return collection;
            //clone.addPiece(startPosition, null);
            //call make move
            //see if moves endPosition = puts piece in check.
        }
//        return piece.pieceMoves(board,startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //find king
        //Call enemyMoves and see if king's start position is in any of the enemy's end positions.
        ChessBoard board = getBoard();
        ChessPiece king;
        for (int i =0; i< 8; i++){
            for (int j = 0; j < 8; j++) {
                king = board.getPiece(new ChessPosition(i+1,j+1));
                if (king ==null){
                    break;
                }
                if(king.getPieceType() == ChessPiece.PieceType.KING&& king.getTeamColor() == teamColor){
                    ChessPosition kingsPosition = new ChessPosition(i,j);
                    Set<ChessPosition> enemyMoveSet= enemyMoveSet(teamColor);
                    if(enemyMoveSet.contains(kingsPosition) ==true){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board =board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * returns a set of all the enemy's moves.
     * @param teamColor our team color.
     * @return set call enemyMoveSet
     */
    public Set<ChessPosition> enemyMoveSet(TeamColor teamColor){
        Set<ChessPosition> enemyMoveset = new HashSet<>();
        ChessPiece piece;
        ChessBoard board = getBoard();
        for (int i =0; i< 8; i++){ //go throughout the board and call pieceMoves and store all moves
            for (int j = 0; j < 8; j++){
                piece = board.getPiece(new ChessPosition(i+1,j+1));
                    if(piece != null &&piece.getTeamColor()!= teamColor) {
                        ChessPosition position=new ChessPosition(i, j);
                        Collection<ChessMove> collection = piece.pieceMoves(board, position);
                        collection.forEach(chessMove -> enemyMoveset.add(chessMove.getEndPosition()));
//                        enemyMoveset.addAll(collection);
                    }
                }
            }
        return enemyMoveset; //store all the moves in a hashset and see if they are valid.
    }
}
