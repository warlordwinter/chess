package chess;

import chess.movement.*;
//import chess.movement.ChessMovementRules;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType PieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor =pieceColor;
        this.PieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return PieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        PieceType type = piece.getPieceType();
        Collection <ChessMove> collection = new ArrayList<>();
        switch (type) {
            case BISHOP:
                ChessMovementRule bishopRules= new BishopMovementRules();
                bishopRules.chessMove(board,myPosition,collection);
                break;
            case ROOK:
                ChessMovementRule rookRules = new RookMovementRules();
                rookRules.chessMove(board,myPosition,collection);
                break;
            case QUEEN:
                ChessMovementRule queenRules = new QueenMovementRules();
                queenRules.chessMove(board,myPosition,collection);
                break;
            case KING:
                ChessMovementRule kingRules = new KingMovementRules();
                kingRules.chessMove(board,myPosition,collection);
                break;
            case KNIGHT:
                ChessMovementRule knightRules = new KnightMovementRules();
                knightRules.chessMove(board,myPosition,collection);
                break;
        }
        return collection;
    }

}
