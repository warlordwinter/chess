package serviceTests;

import dataAccess.MemoryAuthDao;
import dataAccess.MemoryGameDao;
import dataAccess.MemoryUserDao;
import exception.ResponseException;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.JoinGameRequest;
import response.CreateGameResponse;
import response.ListGameResponse;

import java.util.Collection;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
  MemoryAuthDao authDao = new MemoryAuthDao();
  MemoryGameDao gameDao = new MemoryGameDao();
  MemoryUserDao userDao = new MemoryUserDao();
  UserData testUser = new UserData("john","kidney","test@gmail.com");
  UserData testUserTwo = new UserData("mary","jane","test2@gmail.com");
  String token = null;
  String tokenFake = null;
  GameData gameData = new GameData(1234,"mike","","tic-tac",null);

  @BeforeEach
  void setUp() throws ResponseException {
    RegistrationService registrationService = new RegistrationService();
    token = registrationService.registerUser(testUser,userDao,authDao).getAuthToken();
    registrationService.registerUser(testUserTwo,userDao,authDao);
  }

  @Test
  @DisplayName("Create Game Pass Test")
  void createGamePass() throws ResponseException {
    GameService gameService = new GameService();
    CreateGameResponse response = gameService.createGame(token,gameData,gameDao,authDao);
    assertNotNull(response);
  }

  @Test
  @DisplayName("Create Game Fail Test")
  void createGameFail() throws ResponseException {
    GameService gameService = new GameService();
    CreateGameResponse response = gameService.createGame(token,gameData,gameDao,authDao);
    assertNotNull(response);
    assertNotEquals(123,response.getGameID());
  }


  @Test
  @DisplayName("List Game Pass Test")
  void listGamePass() throws ResponseException {
    GameService listService = new GameService();
    GameService gameService = new GameService();
    gameService.createGame(token,gameData,gameDao,authDao);

    ListGameResponse response = listService.listGame(token,authDao,gameDao);

    assertNotNull(response);

    Collection<GameData> collection = response.getGames();
    GameData firstGame = collection.iterator().next();
    assertNotNull(firstGame);
    assertNotNull(firstGame.getGameID());
    assertNotNull(firstGame.getGameName());

  }

  @Test
  @DisplayName("List Game Fail Test")
  void listGameFail() throws ResponseException {
    GameService listService = new GameService();
    GameService gameService = new GameService();
    gameService.createGame(token,gameData,gameDao,authDao);

    Assertions.assertThrows(ResponseException.class, () -> listService.listGame(tokenFake,authDao,gameDao), "Error:unauthorized");
  }

  @Test
  @DisplayName("Join Game Pass Test")
  void joinGamePass() throws ResponseException {
    GameService gameService = new GameService();
    gameService.createGame(token,gameData,gameDao,authDao);
    Collection collection = gameDao.getKeys();
    Object key = collection.iterator().next();
    String gameID = key.toString();
    JoinGameRequest request = new JoinGameRequest("BLACK",gameID);
    gameService.joinGame(request,token,gameDao,authDao);

    GameData gameData1 = gameDao.getGameData(Integer.parseInt(gameID));
    Assertions.assertEquals("john",gameData1.getBlackUsername());
  }

  @Test
  @DisplayName("Join Game Fail Test")
  void joinGameFail() throws ResponseException {
    GameService gameService = new GameService();
    gameService.createGame(token,gameData,gameDao,authDao);
    Assertions.assertThrows(ResponseException.class, () -> gameService.joinGame(new JoinGameRequest("BLACK",null),token,gameDao,authDao), "Error: bad request");

  }
}