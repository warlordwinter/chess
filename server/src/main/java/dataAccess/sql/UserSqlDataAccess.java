package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.UserDao;
import exception.ResponseException;
import model.UserData;
import utility.PasswordHashing;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserSqlDataAccess implements UserDao {


  public UserSqlDataAccess(){
    try {
      DatabaseManager.configureDatabase();
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public void addUser(UserData user) throws DataAccessException {
    String statement = "INSERT INTO userdata (username, password, email) VALUES (?, ?, ?)";
    Connection conn = DatabaseManager.getConnection();
    try(var preparedStatement = conn.prepareStatement(statement)){
    preparedStatement.setString(1, user.getUsername());
    preparedStatement.setString(2, PasswordHashing.hashPassword(user.getPassword()));
    preparedStatement.setString(3, user.getEmail());
    preparedStatement.executeUpdate();

    }catch(SQLException e) {
      throw new DataAccessException(500,e.getMessage());
    }
  }

  @Override
  public UserData getUser(UserData userData) throws DataAccessException {
    Connection conn = DatabaseManager.getConnection();
    String findType = userData.getUsername();
    try (var preparedStatement = conn.prepareStatement("SELECT username,password, email FROM userdata Where username=?")) {
      preparedStatement.setString(1,findType);
      try(var rs = preparedStatement.executeQuery()){
        while(rs.next()){
          var username = rs.getString("username");
          var password = rs.getString("password");
          if(!PasswordHashing.verifyPassword(userData.getPassword(),password)){
            throw new DataAccessException(401,"Error: unauthorized");
          }
          var email = rs.getString("email");
          return new UserData(username,password,email);
        }
      }
    } catch (SQLException e){
      throw new DataAccessException(500, e.getMessage());
    }
    return null;
  }

  @Override
  public void clearUserData() throws DataAccessException {
    Connection conn = DatabaseManager.getConnection();
    try(var preparedStatement = conn.prepareStatement("TRUNCATE userdata")){
      preparedStatement.executeUpdate();
    }catch(SQLException e){
      throw new DataAccessException(500, e.getMessage());
    }

  }

  @Override
  public boolean userInDatabase(UserData userData) throws DataAccessException {
    Connection conn = DatabaseManager.getConnection();
    String findType = userData.getUsername();
    try (var preparedStatement = conn.prepareStatement("SELECT username,password, email FROM userdata Where username=?")) {
      preparedStatement.setString(1,findType);
      try(var rs = preparedStatement.executeQuery()){
        while(rs.next()){
//          System.out.printf("It is in the database");
          return true;
        }
      }
    } catch (SQLException e){
      throw new DataAccessException(500, e.getMessage());
    }
    return false;
  }

}
