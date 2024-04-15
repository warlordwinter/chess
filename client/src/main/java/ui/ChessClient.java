package ui;

import chess.ChessBoard;
import chess.ChessGame;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import sharedResponse.ListGameResponse;
import ui.requests.CreateGamesRequest;
import ui.requests.JoinGameRequest;
import ui.websocket.NotificationHandler;
import ui.websocket.WebSocketFacade;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ChessClient {
  private final String serverUrl;
  private final ServerFacade server;
  private String stringAuthToken;
  private State state = State.SIGNEDOUT;
  private NotificationHandler notificationHandler = new NotificationHandler() {
    @Override
    public void notify(ServerMessage notification) {
      // switch case goes here
    }
  };
  private WebSocketFacade ws;
  private Map<Integer,GameData> gameList = new HashMap<>();

  private int currentGame;

  private boolean gameMenu = false;

  static String[] headers = {"a","b","c","d","e","f","g","h"};
  static String[] columns = {"1","2","3","4","5","6","7","8"};

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
        case "join" -> joinGame(params);
        case "observe" -> observe(params);
        case "redraw" -> redraw();
        case "leave" -> leave();
        case "move" -> makeMove(params);
//        case "resign" ->resign();
        default -> help();
      };
    }catch(ResponseException ex){
      return ex.getMessage();
    }
  }

  private String makeMove(String... params) {
    String username = params[0];
    String password = params[1];
//    ChessPosition startingPosition = new ChessPosition();
//    ChessPosition endingPosition = new ChessPosition();
//    ChessMove chessMove = new ChessMove()''
//    ws.makeMove(stringAuthToken,currentGame,chessMove);
    return String.format("Move Complete Waiting for Opponent");
  }

  private String leave() {
    ws.leave(stringAuthToken,currentGame);
    gameMenu =false;
    return String.format("Leaving the Game");
  }

  public String redraw() {
    ChessBoardUI.buildBoard(ws.getCurrentBoard(),false,headers,columns);
    return String.format("Here is the Board Redrawn");
  }

  public String observe(String[] params) throws ResponseException {
    assertSignedIn();
    GameData gameData=gameList.get(Integer.parseInt(params[0]));
    String gameID = String.valueOf(gameData.getGameID());
    JoinGameRequest joinGameRequest = new JoinGameRequest(null,gameID);
    server.observe(stringAuthToken,joinGameRequest);
    ws.joinObserver(stringAuthToken, gameData.getGameID());
    ChessBoardUI.buildBoard(new ChessBoard(),false,headers,columns);
    return String.format("You are now observing %s",gameID);
  }

  public String signOut() throws ResponseException{
    assertSignedIn();
    server.logout(stringAuthToken);
    stringAuthToken = null;
    ws = null;
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
      gameList.put(gameData.getGameID(),gameData);
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
      gameList.put(count,game);
      result.append(String.format("%d. %s (%s)\n", count++, game.getGameName(), players));
    }
    return result.toString();
  }

  public String joinGame(String ... params)throws ResponseException{
    assertSignedIn();
    GameData gameData=gameList.get(Integer.parseInt(params[0]));
    String color = params[1].toUpperCase();
    String gameID = String.valueOf(gameData.getGameID());
    ChessGame.TeamColor wsColor;

    JoinGameRequest joinGameRequest = new JoinGameRequest(color,gameID);
    server.joinGames(stringAuthToken,joinGameRequest);
    ws = new WebSocketFacade(serverUrl,notificationHandler);
    currentGame = gameData.getGameID();
    gameMenu =true;
    if(color.equals("BLACK")){
      wsColor =ChessGame.TeamColor.BLACK;
    }else{
      wsColor = ChessGame.TeamColor.WHITE;
    }

    ws.joinGame(stringAuthToken,gameData.getGameID(),wsColor);

//    try{sleep(500);}catch(Exception e){
//      System.out.println("Error: " + e.getMessage());
//    }

//    ChessBoardUI.buildBoard(new ChessBoard(),false,headers,columns);

    return String.format("You have joined game %s as the %suser",params[0],params[1]);
  }

  private String getPlayersString(GameData game) {
    String whitePlayer = game.getWhiteUsername() != null ? game.getWhiteUsername() : "Empty";
    String blackPlayer = game.getBlackUsername() != null ? game.getBlackUsername() : "Empty";
    return whitePlayer + " vs " + blackPlayer;
  }


  public String help() {
    if (gameMenu) {
      return gamePlayMenu();
    } else {
      if (state == State.SIGNEDOUT) {
        return """
                    - register <USERNAME> <PASSWORD> <EMAIL> - this is how you register
                    - login
                    - quit - playing chess
                    - help - all possible options
                    """;
      } else {
        return """
                    - create <NAME> - make a new game
                    - join <ID> [WHITE|BLACK|<empty>] - join a game
                    - list - list all of the games
                    - logout - log out
                    - observe <ID> - watch a game
                    - quit - stop playing chess
                    - help - get all the commands
                    """;
      }
    }
  }


  public String gamePlayMenu() {
    return """
        - Help
        - Redraw -redraw the board
        - Leave
        - Make Move - <initial cordinates> <ending cordinates> ex: move 1,2 2,3
        - Resign
        - Highlight Legal Moves
        """;
  }


  private void assertSignedIn() throws ResponseException {
    if (state == State.SIGNEDOUT) {
      throw new ResponseException(400, "You must sign in");
    }
  }
}
