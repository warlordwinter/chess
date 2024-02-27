package dataAccess;

import model.GameData;

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
  public GameData createGame(String gameName) {
    Integer uniqueGameID =Math.abs(UUID.randomUUID().hashCode());
    GameData newGame = new GameData(gameName,uniqueGameID);
    addGame(gameName,newGame);
    return newGame;
  }

  @Override
  public Collection<GameData> listGames() {
//    Collection<GameData> collectionOfGames = new ArrayList<>();
    Collection<GameData> gameData = gameDataBase.values();
    return gameData;
  }

  @Override
  public void addGame(String name, GameData gameData) {
    gameDataBase.put(name,gameData);
  }
}
