package serviceTests;

import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;
import exception.ResponseException;

public class ClearService {

  public void clear(UserDao userDao, GameDao gameDao, AuthDao authDao) throws ResponseException {
    try {
      userDao.clearUserData();
      gameDao.clearGameData();
      authDao.clearAuthData();
    } catch (Exception e) {
      // Log the exception or handle it in an appropriate way
      throw new ResponseException(500, "Clearing Error");
    }
  }
}


