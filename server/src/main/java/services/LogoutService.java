package services;

import dataAccess.AuthDao;
import exception.ResponseException;
import model.AuthData;
import spark.Request;

public class LogoutService {

  public LogoutService logout(Request req, String authHeader, AuthDao authDao) throws ResponseException {
    if (!authDao.verifyAuthToken(authHeader)){
      throw new ResponseException(401,"Error: unauthorized");
    }
    try {
      authDao.deleteAuthToken(authHeader);
    }catch (Exception e){
      throw new ResponseException(500, "Failure to Delete User");
    }
    return null;
  }
}
