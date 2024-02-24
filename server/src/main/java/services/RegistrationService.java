package services;

import dataAccess.AuthDao;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import model.UserData;

public class RegistrationService {


  public AuthData registerUser(UserData user, UserDao userDao, AuthDao authDao) throws ResponseException{


    if (user == null){
      throw new ResponseException(400, "Bad Request");
    }

    if (userDao.getUser(user.getUsername()) != null){
      throw new ResponseException(403, "Error: already taken"); // change this number to be correct
    }

    userDao.addUser(user);
    AuthData token = authDao.createAuthToken();
    authDao.addAuthToken(token.getAuthToken(),token);
    return token;
  }
}
