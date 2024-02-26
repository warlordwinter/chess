package dataAccess;

import model.AuthData;

public interface AuthDao {
  void clearAuthData();

  AuthData createAuthToken(String username);


  void addAuthToken(AuthData authToken);

  public void deleteAuthToken(String authToken);

  public boolean verifyAuthToken(String authToken);
}
