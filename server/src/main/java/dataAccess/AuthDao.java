package dataAccess;

import model.AuthData;

public interface AuthDao {
  void clearAuthData();

  AuthData createAuthToken(String username);

  void addAuthToken(AuthData authToken);

  void deleteAuthToken(String authToken);

  boolean verifyAuthToken(String authToken);

  AuthData getToken(String authID);
}
