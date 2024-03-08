package service;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import response.RegisterResponse;

public class RegistrationService {


  public RegisterResponse registerUser(UserData user, UserDao userDao, AuthDao authDao) throws DataAccessException {


    if (user == null || user.getUsername() ==null|| user.getPassword() ==null || user.getEmail()==null){
      throw new DataAccessException(400, "Error: Bad Request");
    }

    if (userDao.userInDatabase(user)){
      throw new DataAccessException(403, "Error: already taken"); // change this number to be correct
    }

    userDao.addUser(user);
    AuthData token = authDao.createAuthToken(user.getUsername());
    authDao.addAuthToken(token);
    return new RegisterResponse(user.getUsername(),token.getAuthToken());
  }
}
