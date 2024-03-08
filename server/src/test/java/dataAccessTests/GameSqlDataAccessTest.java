package dataAccessTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.sql.AuthSqlDataAccess;
import dataAccess.sql.GameSqlDataAccess;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import requests.JoinGameRequest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GameSqlDataAccessTest {

  GameSqlDataAccess gameSql;
  GameData gameData,gameDataWithoutUsernames,gameDataWithBlackUsername;
  ChessGame chessGame;
  JoinGameRequest joinGameRequest;
  AuthSqlDataAccess authDao;
  String authHeader;

  @BeforeEach
  void setUp() {
    gameSql = new GameSqlDataAccess();
    chessGame = new ChessGame();
    gameData = new GameData(111,"mikeAndIke","Captain_Sparkles","fight For Glory",chessGame);
    gameDataWithBlackUsername = new GameData(1234,null,"Already Taken","fightnight",chessGame);
    gameDataWithoutUsernames =new GameData("Fight Time", 12123);
    joinGameRequest = new JoinGameRequest("BLACK",String.valueOf(12123));
    authDao = new AuthSqlDataAccess();
    AuthData Token =authDao.createAuthToken("john-tron");
    try {
      authDao.addAuthToken(Token);
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
    authHeader = Token.getAuthToken();
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    gameSql.clearGameData();
  }
  @Test
  void clearGameData() {
    try {
      gameSql.addGame(1234,gameData);
      gameSql.clearGameData();
      assertNull(gameSql.getGameData(1234));
    } catch (DataAccessException e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  @Nested
  class createGameTests {
    @Test
    @DisplayName("True Create Game")
    void passingCreateGame() {
      try {
        GameData game1 = gameSql.createGame("battle Time");
        assertEquals("battle Time", game1.getGameName());
      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }

    @Test
    @DisplayName("False Create Game")
    void failedCreateGame() {
      try {
        GameData game1 = gameSql.createGame("battle Time");
        assertNotEquals("battle", game1.getGameName());
      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }
  }

  @Nested
  class listGameTest {

    @Test
    @DisplayName("Passing List Games")
    void passingListGames() {
      try {
        gameSql.addGame(111,gameData);
        gameSql.addGame(12123,gameDataWithoutUsernames);
        Collection list = gameSql.listGames();
        assertEquals(2, list.size());
      } catch (DataAccessException e) {
        throw new RuntimeException(e);
      }
    }
    @Test
    @DisplayName("Failing List Games")
    void failingListGames() {
      try {
        gameSql.addGame(111,gameData);
        Collection list = gameSql.listGames();
        assertNotEquals(2, list.size());
      } catch (DataAccessException e) {
        fail("Unplanned Exception" + e.message());
      }
    }
  }

  @Nested
  class addGameTests {
    @Test
    @DisplayName("True Add Game")
    void passingAddGame() {
      try {
        gameSql.addGame(111,gameData);
        assertEquals("fight For Glory",gameSql.getGameData(111).getGameName());
      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }

    @Test
    @DisplayName("fail Add Game")
    void failedAddGame() {
      try {
        gameSql.addGame(111,gameData);
        gameSql.addGame(111,gameData);
        fail("Expected DataAccessException, but the operation succeeded");
      } catch (DataAccessException e) {
        assertEquals(500,e.getStatusCode());
      }
    }
  }

  @Nested
  class checkGameTest {
    @Test
    @DisplayName("True Check Game Availability Tests")
    void checkGameAvailability() {
      try {
        gameSql.addGame(12123,gameDataWithoutUsernames);
        assertTrue(gameSql.checkGameAvailability(joinGameRequest));
      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }

    @Test
    @DisplayName("Fail Check Game Availability Tests")
    void failCheckGameAvailability() {
      try {
        gameSql.addGame(111,gameData);
        assertFalse(gameSql.checkGameAvailability(joinGameRequest));
      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }
  }
  @Nested
  class testUpdateGame {
    @Test
    @DisplayName("Passing Update Game")
    void passingUpdateGame() {
      try {
        gameSql.addGame(Integer.parseInt(joinGameRequest.gameID()), gameDataWithoutUsernames);
        GameData initialGame = gameSql.getGameData(Integer.parseInt(joinGameRequest.gameID()));
        assertNull(initialGame.getBlackUsername());
        gameSql.updateGame(joinGameRequest, authDao, authHeader);
        GameData updatedGame = gameSql.getGameData(Integer.parseInt(joinGameRequest.gameID()));
        assertEquals("john-tron", updatedGame.getBlackUsername());
      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }

    @Test
    @DisplayName("Failing Update Game")
    void failingUpdateGame() {
      try {
        gameSql.addGame(Integer.parseInt(joinGameRequest.gameID()), gameDataWithBlackUsername);
        gameSql.updateGame(joinGameRequest, authDao, authHeader);
        GameData updatedGame = gameSql.getGameData(Integer.parseInt(joinGameRequest.gameID()));
        assertNotEquals("john-tron", updatedGame.getBlackUsername());
      } catch (DataAccessException e) {
        return;
      }
      fail("Expected DataAccessException was not thrown.");
    }

  }
}
