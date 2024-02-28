package dataAccess;

import model.AuthData;

import java.util.Map;

public interface AuthDao {
  void clearAuthData();

  AuthData createAuthToken(String username);

  void addAuthToken(AuthData authToken);

  void deleteAuthToken(String authToken);

  boolean verifyAuthToken(String authToken);
  Map<String, AuthData> getAuthDataBase();

  AuthData getToken(String authID);
}
