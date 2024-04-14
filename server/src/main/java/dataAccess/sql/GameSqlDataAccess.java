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
      preparedStatement.setInt(1, gameID);
      try (var rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          var gameID1 = rs.getInt("gameID");
          var whiteUsername = rs.getString("whiteUsername");
          var blackUsername = rs.getString("blackUsername");
          var gameName = rs.getString("gameName");
          var game = rs.getString("game");
          Gson gson = new Gson();
//          ChessGame finishedGame = gson.fromJson(game,ChessGame.class);
//          ChessBoard board = finishedGame.getBoard();
          return new GameData(gameID1,whiteUsername,blackUsername,gameName, gson.fromJson(game,ChessGame.class));
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
    ChessGame game = new ChessGame();
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
    String statement = "UPDATE gameData SET blackUsername=?, whiteUsername=?,game =? WHERE gameID =?";
    GameData currentGame = getGameData(Integer.parseInt(request.gameID()));
    if (currentGame == null) {
      throw new DataAccessException(400, "Error: bad request");
    }
    if (request.playerColor() != null && !request.playerColor().isEmpty() && !request.gameID().equals("0")) {
      AuthData authData = authDao.getToken(authHeader);
      if ("BLACK".equals(request.playerColor()) && currentGame.getBlackUsername() == null) {
        currentGame.setBlackUsername(authData.getUsername());
      } else if ("WHITE".equals(request.playerColor()) && currentGame.getWhiteUsername() == null) {
        currentGame.setWhiteUsername(authData.getUsername());
      } else {
        throw new DataAccessException(403, "Error: already taken");
      }
      String serializedGame = new Gson().toJson(currentGame);
      Connection conn = DatabaseManager.getConnection();
      try (var preparedStatement = conn.prepareStatement(statement)) {
        preparedStatement.setString(1, currentGame.getBlackUsername());
        preparedStatement.setString(2, currentGame.getWhiteUsername());
        preparedStatement.setString(3, serializedGame);
        preparedStatement.setInt(4, Integer.parseInt(request.gameID()));
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        throw new DataAccessException(500, e.getMessage());
      }
    }
  }

  @Override
  public void updateGameBoard(int gameId, GameData gameData) throws DataAccessException {
    String statement = "UPDATE gameData SET game=? WHERE gameID=?";
    if (gameData == null) {
      throw new DataAccessException(400, "Error: bad request");
    }

    String serializedGame = new Gson().toJson(gameData);

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
      preparedStatement.setString(1, serializedGame);
      preparedStatement.setInt(2, gameId);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(500, e.getMessage());
    }
  }


}
