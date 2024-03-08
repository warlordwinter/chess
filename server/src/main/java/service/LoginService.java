package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import response.LoginResponse;

import java.util.Collection;
import java.util.Map;

public class LoginService {

  public LoginResponse loginAuthentication(UserData user, UserDao userDao, AuthDao authDao) throws DataAccessException {
    if(!userDao.userInDatabase(user)) {
      throw new DataAccessException(401, "Error: unauthorized");
    }
    AuthData token = authDao.createAuthToken(user.getUsername());
    authDao.addAuthToken(token);
    return new LoginResponse(user.getUsername(),token.getAuthToken());
  }
}
