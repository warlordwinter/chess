package dataAccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.GameDao;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import requests.JoinGameRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

public class GameSqlDataAccess implements GameDao {

  @Override
  public void clearGameData() throws DataAccessException {
    Connection conn = DatabaseManager.getConnection();
    try(var preparedStatement = conn.prepareStatement("TRUNCATE gameData")){
      preparedStatement.executeUpdate();
    }catch(SQLException e){
      throw new DataAccessException(500, e.getMessage());
    }
  }

  @Override
  public GameData getGameData(Integer gameID) throws DataAccessException {
    Connection conn = DatabaseManager.getConnection();
    try (var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername,blackUsername,gameName,game FROM gameData WHERE gameID=?")) {
      preparedStatement.setString(1, String.valueOf(gameID));
      try (var rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          var gameID1 = rs.getString("gameID");
          var whiteUsername = rs.getString("whiteUsername");
          var blackUsername = rs.getString("blackUsername");
          var gameName = rs.getString("gameName");
          var game = rs.getBlob("game");
          return new GameData(Integer.parseInt(gameID1),whiteUsername,blackUsername,gameName, (ChessGame) game);
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException(500, e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
      }
    }
    return null;
  }

  @Override
  public Collection getKeys() {
    return null;
  }

  @Override
  public GameData createGame(String gameName) throws DataAccessException {
    Integer uniqueGameID =Math.abs(UUID.randomUUID().hashCode());
    GameData newGame = new GameData(gameName,uniqueGameID);
    addGame(uniqueGameID,newGame);
    return newGame;
  }

  @Override
  public Collection<GameData> listGames() {
    return null;
  }

  @Override
  public void addGame(Integer gameID, GameData gameData) throws DataAccessException {
    String statement = "INSERT INTO gameData (gameID,whiteUsername,blackUsername,gameName,game) VALUES (?,?,?,?,?)";
    Connection conn = DatabaseManager.getConnection();
    try(var preparedStatement = conn.prepareStatement(statement)){
      preparedStatement.setString(1, String.valueOf(gameID));
      preparedStatement.setString(2, gameData.getWhiteUsername());
      preparedStatement.setString(3, gameData.getBlackUsername());
      preparedStatement.setString(4, gameData.getGameName());
      ChessGame game = gameData.getGame();
      String serializedGame = new Gson().toJson(game);
      preparedStatement.setString(5, serializedGame);
      preparedStatement.executeUpdate();

    }catch(SQLException e) {
      throw new DataAccessException(500,e.getMessage());
    }
  }

  @Override
  public boolean checkGameAvalibility(JoinGameRequest request) {
    return false;
  }

  @Override
  public void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) throws DataAccessException {}

}
