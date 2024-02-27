package serviceTests;

import dataAccess.AuthDao;
import exception.ResponseException;

public class LogoutService {

  public void logout(String authHeader, AuthDao authDao) throws ResponseException {
    if (!authDao.verifyAuthToken(authHeader)){
      throw new ResponseException(401,"Error: unauthorized");
    }
    try {
      authDao.deleteAuthToken(authHeader);
    }catch (Exception e){
      throw new ResponseException(500, "Failure to Delete User");
    }
  }
}
