package dataAccess;

import exception.ResponseException;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDao implements UserDao{


  @Override
  public void addUser(UserData user) throws ResponseException {
    userDataBase.put(user.getUsername(),user);
  }
  @Override
  public UserData getUser(String username, String password) {
    if(!userDataBase.containsKey(username)){
      UserData userData = userDataBase.get(username);
      String passwordStoredInData = userData.getPassword();
      if(passwordStoredInData!=password){
        return null;
      }
    }
    return userDataBase.get(username);
  }



  @Override
  public void clearUserData() {
    userDataBase.clear();
  }

  @Override
  public boolean userInDatabase(UserData userData) {
    UserData databaseCopy = userDataBase.get(userData.getUsername());

    if (databaseCopy == null) {
      return false;
    }

    return userData.equals(databaseCopy);
  }


  private Map<String, UserData> userDataBase =new HashMap<>();




}
