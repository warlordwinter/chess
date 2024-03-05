package dataAccess;

import exception.ResponseException;
import model.UserData;

public interface UserDao {


  void addUser(UserData user) throws DataAccessException;

  UserData getUser(UserData userData);

  void clearUserData();


  boolean userInDatabase(UserData userData);
}
