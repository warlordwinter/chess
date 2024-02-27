package dataAccess;

import model.GameData;
import requests.JoinGameRequest;

import java.util.Collection;

public interface GameDao {
  void clearGameData();

  GameData getGameData(String GameName);

  GameData createGame(String GameName);

  Collection<GameData> listGames();

  void addGame(Integer gameID, GameData gameData);

  boolean checkGameAvalibility(JoinGameRequest request);


  void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader);
}
