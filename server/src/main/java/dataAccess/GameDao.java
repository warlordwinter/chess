package dataAccess;

import chess.ChessGame;
import model.GameData;
import requests.JoinGameRequest;

import java.util.Collection;
import java.util.UUID;

public interface GameDao {
  void clearGameData() throws DataAccessException;

  GameData getGameData(Integer gameID) throws DataAccessException;

  Collection getKeys();

  default GameData createGame(String gameName) throws DataAccessException {
    Integer uniqueGameID = Math.abs(UUID.randomUUID().hashCode());
    ChessGame game = new ChessGame();
    game.createChessBoard();
    GameData newGame = new GameData(gameName, uniqueGameID, game);
    addGame(uniqueGameID, newGame);
    return newGame;
  }

  Collection<GameData> listGames() throws DataAccessException;

  void addGame(Integer gameID, GameData gameData) throws DataAccessException;


  boolean checkGameAvailability(JoinGameRequest request) throws DataAccessException;


  void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) throws DataAccessException;

  public void updateGameBoard(int gameId, GameData gameData) throws DataAccessException;
}
