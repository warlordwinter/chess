package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.UserDao;
import exception.ResponseException;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserSqlDataAccess implements UserDao {

  public void doTransaction() throws DataAccessException{

  }

  public UserSqlDataAccess() throws DataAccessException {
    DatabaseManager.configureDatabase();
  }


  @Override
  public void addUser(UserData user) throws DataAccessException {
    String statement = "INSERT INTO userdata (username, password, email) VALUES (?, ?, ?)";
    Connection conn = DatabaseManager.getConnection();
    try(var preparedStatement = conn.prepareStatement(statement)){
    preparedStatement.setString(1, user.getUsername());
    preparedStatement.setString(2, user.getPassword());
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
  public void clearUserData() {

  }

  @Override
  public boolean userInDatabase(UserData userData) {
    return false;
  }


//  private int executeUpdate(String statement, Object... params) throws ResponseException {
//    try (var conn = DatabaseManager.getConnection()) {
//      try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
//        for (var i = 0; i < params.length; i++) {
//          var param = params[i];
//          if (param instanceof String p) ps.setString(i + 1, p);
//          else if (param instanceof Integer p) ps.setInt(i + 1, p);
//          else if (param instanceof PetType p) ps.setString(i + 1, p.toString());
//          else if (param == null) ps.setNull(i + 1, NULL);
//        }
//        ps.executeUpdate();
//
//        var rs = ps.getGeneratedKeys();
//        if (rs.next()) {
//          return rs.getInt(1);
//        }
//
//        return 0;
//      }
//    } catch (SQLException|DataAccessException e) {
//      throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
//    }
//  }

}

