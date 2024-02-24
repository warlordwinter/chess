package dataAccess;

import model.AuthData;

public interface AuthDao {
  void clearAuthData();

  AuthData createAuthToken();

  public void addAuthToken(String user, AuthData authToken);

}
