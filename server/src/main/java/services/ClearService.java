package services;

import dataAccess.UserDao;

public class ClearService {

  public void clear(UserDao userDao){
    System.out.println("cleared");
    userDao.clearUserData();
    //call the DAO
  }
}
