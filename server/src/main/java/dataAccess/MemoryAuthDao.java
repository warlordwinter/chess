package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDao implements AuthDao{

  private Map<String, AuthData> authDataBase =new HashMap<>();

  @Override
  public void clearAuthData() {
    authDataBase.clear();
  }
}
