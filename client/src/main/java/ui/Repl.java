package ui;

import javax.management.Notification;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.awt.Color.RED;
import static ui.EscapeSequences.SET_BG_COLOR_LIGHT_GREY;

public class Repl {
  private final ChessClient client;
  public Repl(String serverUrl) { client = new ChessClient(serverUrl);}

  public void run() {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    out.print(SET_BG_COLOR_LIGHT_GREY);
    System.out.println("Welcome to Chess. Sign in to start.");
    System.out.print(client.help());

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      printPrompt();
      client.help();
      String line = scanner.nextLine();

      try {
        result = client.eval(line);
        System.out.print(result);
      } catch (Throwable e) {
        var msg = e.toString();
        System.out.print(msg);
      }
    }
    System.out.println();
  }

  public static void printPrompt() {
    System.out.print("\n" + ">>> ");
  }

  public void notify(Notification notification) {
    System.out.println(RED + notification.getMessage());
    printPrompt();
  }

}
