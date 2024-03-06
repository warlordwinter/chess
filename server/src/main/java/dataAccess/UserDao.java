package dataAccess;

import exception.ResponseException;
import model.UserData;

public interface UserDao {


  void addUser(UserData user) throws DataAccessException;

  UserData getUser(UserData userData) throws DataAccessException;

  void clearUserData() throws DataAccessException;


  boolean userInDatabase(UserData userData) throws DataAccessException;
}
