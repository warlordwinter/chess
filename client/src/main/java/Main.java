import chess.*;
import ui.Repl;

public class Main {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece +"Type Help to get Started");

        new Repl(serverUrl).run();
    }
}