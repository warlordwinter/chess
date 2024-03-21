package ui;

import javax.management.Notification;
import java.util.Scanner;

import static java.awt.Color.GREEN;
import static java.awt.Color.RED;

public class Repl {
  private final ChessClient client;
  public Repl(String serverUrl) { client = new ChessClient(serverUrl);}

  public void run() {
    System.out.println("\uD83D\uDC36 Welcome to the pet store. Sign in to start.");
    System.out.print(client.help());

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      printPrompt();
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

  private void printPrompt() {
    System.out.print("\n" + ">>> " + GREEN);
  }

  public void notify(Notification notification) {
    System.out.println(RED + notification.getMessage());
    printPrompt();
  }

}
