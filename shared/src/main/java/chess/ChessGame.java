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
        ChessBoard board = getBoard();
        ChessPiece piece = board.getPiece(startPosition);
        if(piece ==null){
            return null;
        }else {
            ChessBoard clone = new ChessBoard(board);
            ArrayList <ChessMove> inValidMoves = new ArrayList<>();
            ChessPiece clonePiece = clone.getPiece(startPosition);
            Collection<ChessMove> collection = clonePiece.pieceMoves(clone,startPosition);
            TeamColor team = clonePiece.getTeamColor();

            for (ChessMove chessMove : collection){
                ChessPosition endingPosition = chessMove.getEndPosition();
//                ChessPosition adjStartPosition = new ChessPosition(chessMove.getStartPosition().getRow()+1,chessMove.getStartPosition().getColumn());
                clone.addPiece(startPosition, null);
                ChessPiece endingPiece = clone.getPiece(endingPosition);
                clone.addPiece(endingPosition,clonePiece);

                if(isInCheck(team,clone) ==true){
                    inValidMoves.add(chessMove);
                }
                clone.addPiece(endingPosition, endingPiece);
                clone.addPiece(startPosition,clonePiece);

            }
            collection.removeAll(inValidMoves);
            return collection;
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        TeamColor teamColor = getTeamTurn();
        ChessPosition startingPosition = move.getStartPosition();
        ChessPiece piece = board.getPiece(startingPosition);
        TeamColor piececolor = piece.getTeamColor();
//        Collection<ChessMove> validMoves = validMoves(startingPosition);

        if (!piece.getTeamColor().equals(teamColor)) {
            throw new InvalidMoveException();
        }

        if (validMoves(startingPosition).contains(move) != true) {
            throw new InvalidMoveException();
        }

        ChessPosition endingPosition = move.getEndPosition();
        chess.ChessPiece.PieceType type = piece.getPieceType();
        if(move.getPromotionPiece()!=null){
            ChessPiece.PieceType pieceType =move.getPromotionPiece();
            ChessPiece pawnPiece = new ChessPiece(piececolor,pieceType);
            board.addPiece(endingPosition,pawnPiece);
        }else{
            board.addPiece(endingPosition, piece);
        }
        board.addPiece(startingPosition, null);

        if (teamColor == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
//        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheck(teamColor, board);
    }

    public boolean isInCheck(TeamColor teamColor, ChessBoard board){
        ChessPiece king;
        for (int i =0; i< 8; i++){
            for (int j = 0; j < 8; j++) {
                king = board.getPiece(new ChessPosition(i+1,j+1));
                if (king != null && king.getPieceType() == ChessPiece.PieceType.KING && king.getTeamColor().equals(teamColor)) {
                    ChessPosition kingsPosition = new ChessPosition(i+1, j+1);
                    Set<ChessPosition> enemyMoves=enemyMoveSet(teamColor, board);
                    return enemyMoves.contains(kingsPosition);
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor, board)) {
            boolean hasValidMoves = false;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessPiece piece = board.getPiece(new ChessPosition(i+1, j+1));

                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> legalMoves = validMoves(new ChessPosition(i+1, j+1));
                        Set<ChessPosition> enemyMoves = enemyMoveSet(teamColor, board);

                        for (ChessPosition enemyMove : enemyMoves) {
                            ChessPosition enemyEndingPosition = enemyMove;

                            // Filter out ChessMoves with the same ending position as enemy moves
                            if (legalMoves != null) {
                                legalMoves.removeIf(move -> move.getEndPosition().equals(enemyEndingPosition));
                            }
                        }

                        if (legalMoves != null &&!legalMoves.isEmpty()) {
                            hasValidMoves = true;
                            break;
                        }
                    }
                }
            }
            return !hasValidMoves;
        }
        return false;
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
//        team = getTeamTurn();
        if(getTeamTurn()!= teamColor){
            return false;
        }
        ArrayList<ChessMove> movesAvalible = new ArrayList<>();
        if (getTeamTurn() == teamColor){
            for (int i = 0; i < 8; i++) {
                for (int j=0; j < 8; j++) {
                    ChessPiece piece=board.getPiece(new ChessPosition(i, j));
                    if (piece != null && piece.getTeamColor() ==teamColor){
                        ChessPosition startingPosition =new ChessPosition(i,j);
                        Collection<ChessMove>collection = validMoves(startingPosition);
                        movesAvalible.addAll(collection);
                    }
                }
            }
        }
        return movesAvalible.isEmpty();
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
    public Set<ChessPosition> enemyMoveSet(TeamColor teamColor,ChessBoard board){
        Set<ChessPosition> enemyMoveset = new HashSet<>();
        ChessPiece piece;
        for (int i =0; i< 8; i++){ //go throughout the board and call pieceMoves and store all moves
            for (int j = 0; j < 8; j++){
                piece = board.getPiece(new ChessPosition(i+1,j+1));
                    if(piece != null &&piece.getTeamColor()!= teamColor) {
                        ChessPosition position=new ChessPosition(i+1, j+1);
                        if(position.getColumn() <9||position.getRow()<9) {
                            Collection<ChessMove> collection=piece.pieceMoves(board, position);
                            collection.forEach(chessMove -> enemyMoveset.add(chessMove.getEndPosition()));
                        }
                    }
                }
            }
        return enemyMoveset; //store all the moves in a hashset and see if they are valid.
    }
}
