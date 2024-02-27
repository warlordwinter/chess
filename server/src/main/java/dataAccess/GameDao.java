package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDao {
  void clearGameData();

  GameData getGameData(String GameName);

  GameData createGame(String GameName);

  Collection<GameData> listGames();

  void addGame(String name, GameData gameData);
}
