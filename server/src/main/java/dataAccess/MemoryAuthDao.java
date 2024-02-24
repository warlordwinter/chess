package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDao implements AuthDao{

  private Map<String, AuthData> authDataBase =new HashMap<>();

  @Override
  public void clearAuthData() {
    authDataBase.clear();
  }

  @Override
  public AuthData createAuthToken() {
    String auth = UUID.randomUUID().toString();
    AuthData authToken = new AuthData(auth);
    return authToken;
  }

  @Override
  public void addAuthToken(String user, AuthData authToken){
    authDataBase.put(user,authToken);
  }
}
