package ui;

import exception.ResponseException;

import java.util.Arrays;

public class ChessClient {
  private final String serverUrl;
  private final ServerFacade server;

  public ChessClient(String serverUrl) {
    server = new ServerFacade(serverUrl);
    this.serverUrl=serverUrl;
  }

  public String eval(String input){
    try{
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens,1,tokens.length);
      return switch (cmd){
        case "register" -> register(params);
        case "quit" -> "quit";
        default -> help();
      };
    }catch(ResponseException ex){
      return ex.getMessage();
    }
  }

  public String register(String... params) throws ResponseException{
    if(params.length == 3){
      return "hello world";
    }
    throw new ResponseException(400, "Expected register <username>,<password>,<email> - to create an account");
  }

  public String help(){
    return """
                    - register <USERNAME> <PASSWORD> <EMAIL> - this is how you register
                    - login
                    - quit - playing chess
                    - help - all possible options
                    """;
  }

}
