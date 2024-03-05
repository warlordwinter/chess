package dataAccess.sql;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;

import java.util.Collection;

public class GameSqlDataAccess implements GameDao {
  @Override
  public void clearGameData() {

  }

  @Override
  public GameData getGameData(Integer gameID) {
    return null;
  }

  @Override
  public Collection getKeys() {
    return null;
  }

  @Override
  public GameData createGame(String GameName) {
    return null;
  }

  @Override
  public Collection<GameData> listGames() {
    return null;
  }

  @Override
  public void addGame(Integer gameID, GameData gameData) {

  }

  @Override
  public boolean checkGameAvalibility(JoinGameRequest request) {
    return false;
  }

  @Override
  public void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) throws DataAccessException {}

}
