package dataAccess;

import model.AuthData;

public interface AuthDao {
  void clearAuthData();

  AuthData createAuthToken();

  public void addAuthToken(String user, AuthData authToken);

  public void deleteAuthToken(String authToken);

  public boolean verifyAuthToken(AuthData authToken);
}
