package dataAccessTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.sql.GameSqlDataAccess;
import model.GameData;
import org.junit.jupiter.api.*;
import requests.JoinGameRequest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GameSqlDataAccessTest {

  GameSqlDataAccess gameSql;
  GameData gameData,gameDataWithoutUsernames;
  ChessGame chessGame;
  JoinGameRequest joinGameRequest;

  @BeforeEach
  void setUp() {
    gameSql = new GameSqlDataAccess();
    chessGame = new ChessGame();
    gameData = new GameData(111,"mikeAndIke","Captain_Sparkles","fight For Glory",chessGame);
    gameDataWithoutUsernames =new GameData("Fight Time", 12123);
    joinGameRequest = new JoinGameRequest("black",String.valueOf(12123));
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
  @Test
  void updateGame() {
  }
}
