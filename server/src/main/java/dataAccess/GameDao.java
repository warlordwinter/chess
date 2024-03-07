package dataAccess;

import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;

import java.util.Collection;

public interface GameDao {
  void clearGameData() throws DataAccessException;

  GameData getGameData(Integer gameID) throws DataAccessException;

  Collection getKeys();

  GameData createGame(String GameName) throws DataAccessException;

  Collection<GameData> listGames();

  void addGame(Integer gameID, GameData gameData) throws DataAccessException;


  boolean checkGameAvalibility(JoinGameRequest request);

  void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) throws DataAccessException;
}
