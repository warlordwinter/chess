package dataAccess.memory;

import chess.ChessGame;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import model.AuthData;
import model.GameData;
import requests.JoinGameRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryGameDao implements GameDao {

  private Map<Integer, GameData> gameDataBase =new HashMap<>();

  @Override
  public void clearGameData() {
    gameDataBase.clear();
  }

  @Override
  public GameData getGameData(Integer gameID) {
    return gameDataBase.get(gameID);
  }

  @Override
  public Collection getKeys(){
    return gameDataBase.keySet();
  }

  @Override
  public GameData createGame(String gameName) {
    Integer uniqueGameID =Math.abs(UUID.randomUUID().hashCode());
    ChessGame game = new ChessGame();
    game.createChessBoard();
    GameData newGame = new GameData(gameName,uniqueGameID,game);
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
  public boolean checkGameAvailability(JoinGameRequest request) {
    if(gameDataBase.get(Integer.parseInt(request.gameID())) == null){
      return false;
    }
    return true;
  }

  @Override
  public void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) throws DataAccessException {
    GameData currentGame= gameDataBase.get(Integer.parseInt(request.gameID()));
    if(currentGame ==null){
      throw new DataAccessException(400, "Error: bad request");
    }
    if (request.playerColor() == null) {
    } else if (!request.playerColor().isEmpty() && !request.gameID().equals("0")) {
      AuthData authData = authDao.getToken(authHeader);
      if (currentGame.getBlackUsername() == null && request.playerColor().equals("BLACK")) {
        currentGame.setBlackUsername(authData.getUsername());
      } else if (currentGame.getWhiteUsername() == null && request.playerColor().equals("WHITE")) {
        currentGame.setWhiteUsername(authData.getUsername());
      } else {
        throw new DataAccessException(403, "Error: already taken");
      }
      gameDataBase.put(Integer.parseInt(request.gameID()), currentGame);
    }
  }

  @Override
  public void updateGameBoard(int gameId, GameData gameData) throws DataAccessException{
  }
}
