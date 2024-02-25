package services;

import dataAccess.AuthDao;
import exception.ResponseException;
import model.AuthData;

public class LogoutService {

  public void logout(AuthData authData, AuthDao authDao) throws ResponseException {
    if (!authDao.verifyAuthToken(authData)){
      throw new ResponseException(401,"Error: unauthorized");
    }
    try {
      authDao.deleteAuthToken(authData.toString());
    }catch (Exception e){
      throw new ResponseException(500, "Failure to Delete User");
    }
  }
}
