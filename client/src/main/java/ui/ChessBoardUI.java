package ui;

import chess.ChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ChessBoardUI {
  ChessBoard chessBoard = new ChessBoard();
  private boolean reverseTheBoard = false;
  private static final String EMPTY = " ";
  static String[] headers = { "a","b","c","d","e","f","g","h" };
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

  static void buildBoard(ChessBoard chesBoard, boolean reverseTheBoard, String[] headers, String[] column){
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    drawHeaders(headers,out);

  }

  private static void drawHeaders(String[] headers, PrintStream out) {
    setGrey(out);

    for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
      drawHeader(out, headers[boardCol]);

      if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
      }
    }
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
    out.print(SET_TEXT_COLOR_GREEN);

    out.print(player);

    setGrey(out);
  }

  private static void setGrey(PrintStream out) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);
  }
  private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLACK);
  }


}
