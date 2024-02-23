package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDao implements UserDao{


  @Override
  public void clearUserData() {
    userDataBase.clear();
  }

  private Map<String, UserData> userDataBase =new HashMap<>();


}
