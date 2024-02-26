package dataAccess;

import exception.ResponseException;
import model.UserData;

public interface UserDao {


  void addUser(UserData user) throws ResponseException;

  UserData getUser(String username, String password);

  void clearUserData();


  boolean userInDatabase(UserData userData);
}
