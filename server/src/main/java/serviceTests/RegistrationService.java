package serviceTests;

import dataAccess.AuthDao;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import response.RegisterResponse;

public class RegistrationService {


  public RegisterResponse registerUser(UserData user, UserDao userDao, AuthDao authDao) throws ResponseException{


    if (user == null || user.getUsername() ==null|| user.getPassword() ==null || user.getEmail()==null){
      throw new ResponseException(400, "Error: Bad Request");
    }

    if (userDao.userInDatabase(user)){
      throw new ResponseException(403, "Error: already taken"); // change this number to be correct
    }

    userDao.addUser(user);
    AuthData token = authDao.createAuthToken(user.getUsername());
    authDao.addAuthToken(token);
    return new RegisterResponse(user.getUsername(),token.getAuthToken());
  }
}
