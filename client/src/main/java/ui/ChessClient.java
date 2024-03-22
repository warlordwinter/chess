package ui;

import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import ui.requests.CreateGamesRequest;
import ui.response.ListGameResponse;

import java.util.Arrays;
import java.util.Collection;

public class ChessClient {
  private final String serverUrl;
  private final ServerFacade server;
  private String stringAuthToken;
  private State state = State.SIGNEDOUT;

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
        case "login" -> login(params);
        case "logout" -> signOut();
        case "create" -> createGame(params);
        case "list" -> listGame();
        default -> help();
      };
    }catch(ResponseException ex){
      return ex.getMessage();
    }
  }

  public String signOut() throws ResponseException{
    assertSignedIn();
    server.logout(stringAuthToken);
    stringAuthToken = null;
    state=State.SIGNEDOUT;
    return String.format("Logged out!");
  }

  public String register(String... params) throws ResponseException{
    if(params.length == 3){
      String username = params[0];
      String password = params[1];
      String email = params [2];
      UserData userData =new UserData(username,password,email);
      AuthData authData = server.register(userData);
      stringAuthToken = authData.getAuthToken();
      state = State.SIGNEDIN;
      return String.format("Welcome %s", username);
    }
    throw new ResponseException(400, "Expected register <username>,<password>,<email> - to create an account");
  }

  public String login(String... params) {
    try {
      if (params.length == 2) {
        UserData userData = new UserData(params[0], params[1], null);
        AuthData authData = server.login(userData);
        stringAuthToken = authData.getAuthToken();
        state = State.SIGNEDIN;
        return String.format("Welcome Back %s", params[0]);
      }
      throw new ResponseException(400, "Expected login <username> <password> - to login to your account");
    } catch (ResponseException ex) {
      return ex.getMessage();
    }
  }

  public String createGame(String ... params) throws ResponseException {
    if (params.length == 1) {
      String gameName = params[0];
      CreateGamesRequest createGamesRequest = new CreateGamesRequest(gameName);
      GameData gameData= server.createGames(stringAuthToken, createGamesRequest);
      return String.format("Created game: %s with ID: %s", params[0], gameData.getGameID());
    } else {
      throw new ResponseException(400, "Expected: create <NAME>");
    }
  }

  public String listGame()throws ResponseException{
    assertSignedIn();
    ListGameResponse listGameResponse = server.listGames(stringAuthToken);
    Collection<GameData> games=listGameResponse.getGames();

    StringBuilder result = new StringBuilder("Games:\n");
    int count = 1;
    for (GameData game : games) {
      String players = getPlayersString(game);
      result.append(String.format("%d. %s (%s)\n", count++, game.getGameName(), players));
    }
    return result.toString();
  }

  private String getPlayersString(GameData game) {
    String whitePlayer = game.getWhiteUsername() != null ? game.getWhiteUsername() : "Empty";
    String blackPlayer = game.getBlackUsername() != null ? game.getBlackUsername() : "Empty";
    return whitePlayer + " vs " + blackPlayer;
  }


  public String help(){
    if (state == State.SIGNEDOUT) {
      return """
              - register <USERNAME> <PASSWORD> <EMAIL> - this is how you register
              - login
              - quit - playing chess
              - help - all possible options
              """;
    }
    return  """
                - create <NAME> - make a new game
                - join <ID> [WHITE|BLACK|<empty>] -a game
                - list - all of the games
                - logout
                - observe <ID> - Watch a game
                - quit -stop playing chess
                - help - get all the commands
                """;
  }

  private void assertSignedIn() throws ResponseException {
    if (state == State.SIGNEDOUT) {
      throw new ResponseException(400, "You must sign in");
    }
  }
}
