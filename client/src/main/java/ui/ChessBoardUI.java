package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ChessBoardUI {
  ChessBoard chessBoard = new ChessBoard();
  private boolean reverseTheBoard = false;
  private static final String EMPTY = " ";
  static String[] headers = {"a","b","c","d","e","f","g","h"};
  static String[] columns = {"1","2","3","4","5","6","7","8"};
  private static final int BOARD_SIZE_IN_SQUARES = 8;
  private static final int LINE_WIDTH_IN_CHARS = 1;


  public static void main(String[] args) {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    out.print(ERASE_SCREEN);

    buildBoard(new ChessBoard(),false, headers, columns);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  public static void buildBoard(ChessBoard chessBoard, boolean reverseTheBoard, String[] headers, String[] column){
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    chessBoard.resetBoard(); //remember to remove this line later
    drawHeaders(headers,out,reverseTheBoard);
    printRow(out,column,chessBoard,reverseTheBoard);
    drawHeaders(headers,out,reverseTheBoard);

    setBlack(out); // reverse board
    out.println();
    drawHeaders(headers,out,reverseTheBoard);
    reverseTheBoard = true;
    printRow(out,column,chessBoard,reverseTheBoard);
    drawHeaders(headers,out,reverseTheBoard);
    setWhite(out);
  }

  private static void drawHeaders(String[] headers, PrintStream out, Boolean reverseTheBoard) {
    setGrey(out);
    out.print(EMPTY.repeat(3));

    if(reverseTheBoard==false){
      for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
        drawHeader(out, headers[boardCol]);

        if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
          out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
        }
      }
    }else {
      for (int boardCol=BOARD_SIZE_IN_SQUARES - 1; boardCol >= 0; --boardCol) {
        drawHeader(out, headers[boardCol]);

        if (boardCol > 0) {
          out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
        }
      }
    }

    out.print(EMPTY.repeat(4));
    setBlack(out);
    out.println();
  }


  private static void drawHeader(PrintStream out, String header) {
    out.print(EMPTY.repeat(1));
    printHeaderText(out, header);
    out.print(EMPTY.repeat(1));
  }

  private static void printHeaderText(PrintStream out, String player) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);

    out.print(player);

    setGrey(out);
  }

  private static void printRow(PrintStream out, String[] columns, ChessBoard chessBoard, boolean reverseTheBoard){
    if (reverseTheBoard == false) {
      for (int row=0; row < 8; row++) { //this is where I will go through the board
        drawColumn(out, columns[row]);
        for (int col=0; col < 8; col++) {
          rowCreator(out, columns, chessBoard, row, col);
        }
        drawColumn(out, columns[row]);
        setBlack(out);
        out.println();
      }
    } else{
      for (int row = 7; row >= 0; row--) {
        drawColumn(out, columns[row]);
        for (int col=7; col >= 0; col--) {
          rowCreator(out, columns, chessBoard, row, col);
        }
        drawColumn(out, columns[row]);
        setBlack(out);
        out.println();
      }
    }
  }

  private static void rowCreator(PrintStream out, String[] columns, ChessBoard chessBoard, int row, int col){
    tileColor(out,row,col);
    out.print(EMPTY);
    ChessPosition chessPosition = new ChessPosition(row,col);
    ChessPiece currentPiece = chessBoard.getPieceForUI(chessPosition);
    if(currentPiece!=null) {
      ChessPiece.PieceType type = currentPiece.getPieceType();
      ChessGame.TeamColor color = currentPiece.getTeamColor();
      placeSquare(type, color, out);
    }else{
      out.print(EMPTY.repeat(1));
    }
    out.print(EMPTY.repeat(2));
  }

  private static void tileColor(PrintStream out, int row, int col) {
    if((row + col) % 2 == 0){
      out.print(SET_BG_COLOR_BLACK);
    } else{
      out.print(SET_BG_COLOR_WHITE);
    }
  }

  private static void placeSquare(ChessPiece.PieceType type, ChessGame.TeamColor color, PrintStream out) {
    String letter = "";
    switch (type) {
      case BISHOP:
        letter = "B";
        break;
      case ROOK:
        letter = "R";
        break;
      case QUEEN:
        letter = "Q";
        break;
      case KING:
        letter = "K";
        break;
      case KNIGHT:
        letter = "N";
        break;
      case PAWN:
        letter = "P";
        break;
      default: letter = "";
    }
    if(color == ChessGame.TeamColor.BLACK){
      out.print(SET_TEXT_COLOR_RED);
      out.print(letter);
    }else{
      out.print(SET_TEXT_COLOR_BLUE);
      out.print(letter);
    }
  }

  private static void drawColumn(PrintStream out, String value) {
    setGrey(out);
    out.print(EMPTY.repeat(1));
    printHeaderText(out, value);
    out.print(EMPTY.repeat(1));
  }
  private static void setGrey(PrintStream out) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);
  }
  private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLACK);
  }
  private static void setWhite(PrintStream out) {
//    out.print(SET_BG_COLOR_WHITE);
    out.print(SET_TEXT_COLOR_WHITE);
  }

}
