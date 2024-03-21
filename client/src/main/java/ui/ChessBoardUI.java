package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessBoardUI {

  private static final int BOARD_SIZE_IN_SQUARES = 8;
  private static final int SQUARE_SIZE_IN_CHARS = 3;
  private static final int LINE_WIDTH_IN_CHARS = 3;
  private static final String EMPTY = "   ";
  private static final String X = " X ";
  private static final String O = " O ";
  private static Random rand = new Random();
  private static String color=null;


  public static void main(String[] args) {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    out.print(ERASE_SCREEN);

    drawHeaders(out);

    drawChessBoard(out);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  private static void drawHeaders(PrintStream out) {
    setBlack(out);

    String[] headers = {" a "," b "," c "," d "," e "," f "," g "," h ", };
    for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
      drawHeader(out, headers[boardCol]);
    }
    out.println();
  }

  private static void drawHeader(PrintStream out, String headerText) {
    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

    out.print(EMPTY.repeat(prefixLength));
    printHeaderText(out, headerText);
    out.print(EMPTY.repeat(suffixLength));
  }

  private static void printHeaderText(PrintStream out, String player) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_WHITE);

    out.print(player);

    setBlack(out);
  }

  private static void drawChessBoard(PrintStream out) {
    String color = "WHITE"; // Use a different name for the variable to avoid shadowing
    for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
      drawRowOfSquares(out, color);
      color = toggleColor(color);

      if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
        setBlack(out);
      }
    }
  }

  private static void drawRowOfSquares(PrintStream out, String color) {
    String currentColor = color;
    for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
      for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
        drawSquare(out, currentColor);
        currentColor = toggleColor(currentColor);
      }
      out.println();
    }
  }

  private static void drawSquare(PrintStream out, String color){
    out.print(color.equals("WHITE") ? SET_BG_COLOR_WHITE : SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLACK);
    out.print(color.equals("WHITE") ? X : O);
  }

  private static String toggleColor(String color) {
    return color.equals("WHITE") ? "BLACK" : "WHITE";
  }
  private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLACK);
  }

}
