package dataAccess.memory;

import dataAccess.AuthDao;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDao implements AuthDao {

  public Map<String, AuthData> authDataBase =new HashMap<>();

  @Override
  public void clearAuthData() {
    authDataBase.clear();
  }

  @Override
  public AuthData createAuthToken(String username) {
    String auth = UUID.randomUUID().toString();
    AuthData authToken = new AuthData(auth,username);
    return authToken;
  }
  public Map<String, AuthData> getAuthDataBase(){
    return authDataBase;
  }

  @Override
  public void addAuthToken(AuthData authToken){
    authDataBase.put(authToken.getAuthToken(),authToken);
  }

  @Override
  public void deleteAuthToken(String authToken) {
    authDataBase.remove(authToken);
  }

  @Override
  public boolean verifyAuthToken(String authToken) {
    if(authDataBase.containsKey(authToken)){
      return true;
    }
    return false;
  }

  @Override
  public AuthData getToken(String authID) {
    return authDataBase.get(authID);
  }
}
