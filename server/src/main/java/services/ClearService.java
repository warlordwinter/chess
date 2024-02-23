package services;

import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;

public class ClearService {

  public void clear(UserDao userDao, GameDao gameDao, AuthDao authDao){
    System.out.println("cleared");
    userDao.clearUserData();
    gameDao.clearGameData();
    authDao.clearAuthData();
    //call the DAO
  }
}
