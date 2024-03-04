package serviceTests;

import dataAccess.memory.MemoryAuthDao;
import dataAccess.memory.MemoryGameDao;
import dataAccess.memory.MemoryUserDao;
import exception.ResponseException;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClearServiceTest {
  MemoryAuthDao authDao = new MemoryAuthDao();
  MemoryGameDao gameDao = new MemoryGameDao();
  MemoryUserDao userDao = new MemoryUserDao();
  UserData testUser = new UserData("john","kidney","test.gmail.com");

  @BeforeEach
  void setUp() throws ResponseException {
    RegistrationService registrationService = new RegistrationService();
    registrationService.registerUser(testUser,userDao,authDao);
  }

  @Test
  @DisplayName("Clear Pass Test")
  void clearPass() throws ResponseException {
    ClearService clearService = new ClearService();
    clearService.clear(userDao,gameDao,authDao);
    Assertions.assertEquals(userDao.userInDatabase(testUser)==false,userDao.userInDatabase(testUser)==false);
  }

  @Test
  @DisplayName("Clear Fail Test")
  void clearFail() throws ResponseException {
    ClearService clearService = new ClearService();
    clearService.clear(userDao,gameDao,authDao);
    RegistrationService registrationService = new RegistrationService();
    registrationService.registerUser(testUser,userDao,authDao);
    Assertions.assertEquals(userDao.userInDatabase(testUser)==true,userDao.userInDatabase(testUser)==true);
  }
}