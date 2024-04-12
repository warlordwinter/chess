package dataAccess;

import model.GameData;
import requests.JoinGameRequest;

import java.util.Collection;

public interface GameDao {
  void clearGameData() throws DataAccessException;

  GameData getGameData(Integer gameID) throws DataAccessException;

  Collection getKeys();

  GameData createGame(String GameName) throws DataAccessException;

  Collection<GameData> listGames() throws DataAccessException;

  void addGame(Integer gameID, GameData gameData) throws DataAccessException;


  boolean checkGameAvailability(JoinGameRequest request) throws DataAccessException;


  void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) throws DataAccessException;

  public void updateGameBoard(int gameId, GameData gameData) throws DataAccessException;
}
