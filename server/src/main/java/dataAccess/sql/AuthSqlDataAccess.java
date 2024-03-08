package dataAccess.sql;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static dataAccess.DatabaseManager.user;

public class AuthSqlDataAccess implements AuthDao {
  @Override
  public void clearAuthData() throws DataAccessException {
    Connection conn = DatabaseManager.getConnection();
    try(var preparedStatement = conn.prepareStatement("TRUNCATE authData")){
      preparedStatement.executeUpdate();
    }catch(SQLException e){
      throw new DataAccessException(500, e.getMessage());
    }
  }

  @Override
  public AuthData createAuthToken(String username) {
    String auth = UUID.randomUUID().toString();
    AuthData authToken = new AuthData(auth,username);
    return authToken;
  }

  @Override
  public void addAuthToken(AuthData authToken) throws DataAccessException {
    String statement = "INSERT INTO authData (authtoken,username) VALUES (?, ?)";
    Connection conn = DatabaseManager.getConnection();
    try(var preparedStatement = conn.prepareStatement(statement)){
      preparedStatement.setString(1, authToken.getAuthToken());
      preparedStatement.setString(2, authToken.getUsername());
      preparedStatement.executeUpdate();

    }catch(SQLException e) {
      throw new DataAccessException(500,e.getMessage());
    }
  }

  @Override
  public void deleteAuthToken(String authToken) throws DataAccessException{
    Connection conn = DatabaseManager.getConnection();
    try (var preparedStatement = conn.prepareStatement("DELETE FROM authData WHERE authtoken=?")) {
      preparedStatement.setString(1, authToken);
      preparedStatement.executeUpdate();
    } catch(SQLException e){
      throw new DataAccessException(500, e.getMessage());
    }
  }

  @Override
  public boolean verifyAuthToken(String authToken) throws DataAccessException {
    Connection conn = DatabaseManager.getConnection();
    try (var preparedStatement = conn.prepareStatement("SELECT authtoken FROM authData WHERE authtoken=?")) {
      preparedStatement.setString(1, authToken);
      try (var rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
//          System.out.println("The auth token is in the database");
          return true;
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException(500, e.getMessage());
    }
    return false;
  }


  @Override
  public AuthData getToken(String authID) throws DataAccessException {
    Connection conn = DatabaseManager.getConnection();
    try (var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM authData WHERE authtoken=?")) {
      preparedStatement.setString(1, authID);
      try (var rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          var authTokenID = rs.getString("authToken");
          var username = rs.getString("username");
          return new AuthData(authTokenID, username);
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


}
