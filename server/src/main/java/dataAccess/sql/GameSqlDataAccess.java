package dataAccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.GameDao;
import model.AuthData;
import model.GameData;
import requests.JoinGameRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
          var game = rs.getString("game");
          Gson gson = new Gson();
          ChessGame finishedGame = gson.fromJson(game,ChessGame.class);

          return new GameData(Integer.parseInt(gameID1),whiteUsername,blackUsername,gameName, finishedGame);
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
  public Collection<GameData> listGames() throws DataAccessException {
    String statement ="SELECT gameID, whiteUsername, blackUsername, gameName, game FROM gameData";
    Collection<GameData> gameDataCollection = new ArrayList<>();
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement preparedStatement = conn.prepareStatement(statement);
         ResultSet rs = preparedStatement.executeQuery()) {

      while (rs.next()) {
        int gameID = rs.getInt("gameID");
        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");
        String gameName = rs.getString("gameName");
        String game = rs.getString("game");
        Gson gson = new Gson();
        ChessGame finishedGame = gson.fromJson(game,ChessGame.class);
        GameData gameData = new GameData(gameID, whiteUsername, blackUsername, gameName, finishedGame);
        gameDataCollection.add(gameData);
      }

    } catch (SQLException | DataAccessException e) {
      throw new DataAccessException(500, e.getMessage());
    }
    return gameDataCollection;
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
  public boolean checkGameAvailability(JoinGameRequest request) throws DataAccessException {
    Connection conn = DatabaseManager.getConnection();
    try (var preparedStatement = conn.prepareStatement("SELECT whiteUsername, blackUsername FROM gameData WHERE gameID = ?")) {
      preparedStatement.setString(1, request.gameID());
      try (var rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          String blackUsername = rs.getString("blackUsername");
          String whiteUsername = rs.getString("whiteUsername");

          if (blackUsername == null || whiteUsername == null) {
            return true;
          }
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException(500, e.getMessage());
    }
    return false;
  }

  @Override
  public void updateGame(JoinGameRequest request, AuthDao authDao, String authHeader) throws DataAccessException {
    String statement = "UPDATE gameData SET gameID=?, ";
    GameData currentGame = getGameData(Integer.parseInt(request.gameID()));
    int change = 0;
    if (currentGame == null) {
      throw new DataAccessException(400, "Error: bad request");
    }
    if (request.playerColor() != null && !request.playerColor().isEmpty() && !request.gameID().equals("0")) {
      AuthData authData = authDao.getToken(authHeader);
      if (currentGame.getBlackUsername() == null && request.playerColor().equals("BLACK")) {
        currentGame.setBlackUsername(authData.getUsername());
        statement += "blackUsername=? ";
        change += 1;
      } else if (currentGame.getWhiteUsername() == null && request.playerColor().equals("WHITE")) {
        currentGame.setWhiteUsername(authData.getUsername());
        statement += "whiteUsername=? ";
        change += 1;
      } else {
        throw new DataAccessException(403, "Error: already taken");
      }
      String serializedGame = new Gson().toJson(currentGame);
      Connection conn = DatabaseManager.getConnection();
      try (var preparedStatement = conn.prepareStatement(statement + "WHERE gameID=?")) {
        preparedStatement.setInt(1, Integer.parseInt(request.gameID()));
        preparedStatement.setString(2, serializedGame);
        if (change == 1) {
          preparedStatement.setString(3, authData.getUsername());
        }
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        throw new DataAccessException(500, e.getMessage());
      }
    }
  }

}
