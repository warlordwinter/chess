package chess;

import chess.movement.*;


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
                ChessMovementRules bishopRules= new BishopMovementRules();
                bishopRules.chessMove(board,myPosition,collection);
//            case KING:
//                ChessMovementRules.King King= new ChessMovementRules.King();
//                return King.chessMove(board,myPosition);
//            case ROOK:
//                ChessMovementRules.Rook Rook = new ChessMovementRules.Rook();
//                return Rook.chessMove(board,myPosition);
//            case PAWN:
//                ChessMovementRules.Pawn Pawn= new ChessMovementRules.Pawn();
//                return Pawn.chessMove(board,myPosition);
//            case QUEEN:
//                ChessMovementRules.Queen Queen = new ChessMovementRules.Queen();
//                return Queen.chessMove(board,myPosition);
//            case KNIGHT:
//                ChessMovementRules.Knight Knight = new ChessMovementRules.Knight();
//                return Knight.chessMove(board,myPosition);
//            default:
//                System.out.println("Unknown Piece type");
//                break;
        }
        return collection;
    }

}
