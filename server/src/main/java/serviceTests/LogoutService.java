package serviceTests;

import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import exception.ResponseException;

public class LogoutService {

  public void logout(String authHeader, AuthDao authDao) throws DataAccessException {
    if (!authDao.verifyAuthToken(authHeader)){
      throw new DataAccessException(401,"Error: unauthorized");
    }
    try {
      authDao.deleteAuthToken(authHeader);
    }catch (Exception e){
      throw new DataAccessException(500, "Failure to Delete User");
    }
  }
}
