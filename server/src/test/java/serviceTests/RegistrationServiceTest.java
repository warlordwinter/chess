package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDao;
import dataAccess.MemoryGameDao;
import dataAccess.MemoryUserDao;
import exception.ResponseException;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import response.RegisterResponse;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {
  MemoryAuthDao authDao = new MemoryAuthDao();
  MemoryGameDao gameDao = new MemoryGameDao();
  MemoryUserDao userDao = new MemoryUserDao();
  UserData testUser = new UserData("john","kidney","test.gmail.com");

  @Test
  @DisplayName("Register User Pass")
  void registerUserPass() throws ResponseException {
    RegistrationService registrationService = new RegistrationService();
    registrationService.registerUser(testUser,userDao,authDao);
    Assertions.assertEquals(userDao.userInDatabase(testUser)==true,userDao.userInDatabase(testUser)==true);
  }

  @Test
  @DisplayName("Register User Fail")
  void registerUserFail() throws ResponseException {
    RegistrationService registrationService = new RegistrationService();
    UserData testUserFail = new UserData("john",null,null);
    Assertions.assertThrows(ResponseException.class, () -> registrationService.registerUser(testUserFail,userDao,authDao), "Error: Bad Request");
  }
}