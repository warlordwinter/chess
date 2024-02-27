package dataAccess;

import model.GameData;
import model.UserData;

import java.util.*;

public class MemoryGameDao implements GameDao{

  private Map<String, GameData> gameDataBase =new HashMap<>();

  @Override
  public void clearGameData() {
    gameDataBase.clear();
  }

  @Override
  public GameData getGameData(String gameName) {
    return null;
  }

  @Override
  public GameData createGame(String GameName) {
    Integer uniqueGameID =Math.abs(UUID.randomUUID().hashCode());
    return new GameData(GameName,uniqueGameID);
  }

  @Override
  public Collection<GameData> listGames() {
    gameDataBase.values();
    Collection<GameData> collectionOfGames = new ArrayList<>();
    return collectionOfGames;
  }
}
