package dataAccess;

import model.GameData;

public interface GameDao {
  void clearGameData();

  GameData getGameData(String GameName);

  GameData createGame(String GameName);
}
