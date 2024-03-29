package dataAccess.memory;


import dataAccess.UserDao;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDao implements UserDao {


  @Override
  public void addUser(UserData user){
    userDataBase.put(user.getUsername(),user);
  }
  @Override
  public UserData getUser(UserData userData) {
    return userDataBase.get(userData.getUsername());
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

    if(databaseCopy.getPassword().equals(userData.getPassword())){
      return true;
    }
    return false;
  }


  private Map<String, UserData> userDataBase =new HashMap<>();




}
