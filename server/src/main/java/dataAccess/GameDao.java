package dataAccess;

import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;

import java.util.Collection;

public interface GameDao {
  void clearGameData();

  GameData getGameData(Integer gameID);

  Collection getKeys();

  GameData createGame(String GameName);

  Collection<GameData> listGames();

  void addGame(Integer gameID, GameData gameData);


  boolean checkGameAvalibility(JoinGameRequest request);

  void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) throws ResponseException;
}
