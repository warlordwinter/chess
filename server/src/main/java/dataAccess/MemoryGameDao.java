package dataAccess;

import model.AuthData;
import model.GameData;
import requests.JoinGameRequest;

import java.util.*;

public class MemoryGameDao implements GameDao{

  private Map<Integer, GameData> gameDataBase =new HashMap<>();

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
    addGame(uniqueGameID,newGame);
    return newGame;
  }

  @Override
  public Collection<GameData> listGames() {
    Collection<GameData> gameData = gameDataBase.values();
    return gameData;
  }

  @Override
  public void addGame(Integer gameID, GameData gameData) {
    gameDataBase.put(gameID,gameData);
  }

  @Override
  public boolean checkGameAvalibility(JoinGameRequest request) {
    if(gameDataBase.get(Integer.parseInt(request.gameID())) == null){
      return false;
    }
    return true;
  }

  @Override
  public void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) {
    GameData currentGame= gameDataBase.get(Integer.parseInt(request.gameID()));
    if(request.playerColor()!=""){
      if(currentGame.getBlackUsername().equals("") && request.playerColor().equals("BLACK")){
        AuthData authData = authDao.getToken(authHeader);
        currentGame.setBlackUsername(authData.getUsername());
      }
      if(currentGame.getWhiteUsername().equals("") && request.playerColor().equals("WHITE")){
        AuthData authData = authDao.getToken(authHeader);
        currentGame.setWhiteUsername(authData.getUsername());
      }
      else{
        //observer
      }
      gameDataBase.put(Integer.parseInt(request.gameID()), currentGame);
    }
  }
}
