package dataAccess;

import exception.ResponseException;
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
  public void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) throws ResponseException {
    GameData currentGame= gameDataBase.get(Integer.parseInt(request.gameID()));
    if(currentGame ==null){
      throw new ResponseException(400, "Error: bad request");
    }
    if (request.playerColor() == null) {
    } else if (!request.playerColor().isEmpty() && !request.gameID().equals("0")) {
      AuthData authData = authDao.getToken(authHeader);
      if (currentGame.getBlackUsername() == null && request.playerColor().equals("BLACK")) {
        currentGame.setBlackUsername(authData.getUsername());
      } else if (currentGame.getWhiteUsername() == null && request.playerColor().equals("WHITE")) {
        currentGame.setWhiteUsername(authData.getUsername());
      } else {
        throw new ResponseException(403, "Error: already taken");
      }
      gameDataBase.put(Integer.parseInt(request.gameID()), currentGame);
    }
  }
}
