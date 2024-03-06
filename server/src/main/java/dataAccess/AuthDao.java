package dataAccess;

import model.AuthData;

import java.util.Map;

public interface AuthDao {
  void clearAuthData() throws DataAccessException;

  AuthData createAuthToken(String username);

  void addAuthToken(AuthData authToken) throws DataAccessException;

  void deleteAuthToken(String authToken);

  boolean verifyAuthToken(String authToken) throws DataAccessException;
  Map<String, AuthData> getAuthDataBase();

  AuthData getToken(String authID) throws DataAccessException;
}
