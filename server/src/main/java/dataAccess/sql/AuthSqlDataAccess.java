package dataAccess.sql;

import dataAccess.AuthDao;
import model.AuthData;

import java.util.Map;

public class AuthSqlDataAccess implements AuthDao {
  @Override
  public void clearAuthData() {

  }

  @Override
  public AuthData createAuthToken(String username) {
    return null;
  }

  @Override
  public void addAuthToken(AuthData authToken) {

  }

  @Override
  public void deleteAuthToken(String authToken) {

  }

  @Override
  public boolean verifyAuthToken(String authToken) {
    return false;
  }

  @Override
  public Map<String, AuthData> getAuthDataBase() {
    return null;
  }

  @Override
  public AuthData getToken(String authID) {
    return null;
  }

}
