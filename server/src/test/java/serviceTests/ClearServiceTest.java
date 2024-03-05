package serviceTests;

import dataAccess.DataAccessException;
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
  void setUp() throws DataAccessException {
    RegistrationService registrationService = new RegistrationService();
    registrationService.registerUser(testUser,userDao,authDao);
  }

  @Test
  @DisplayName("Clear Pass Test")
  void clearPass() throws DataAccessException {
    ClearService clearService = new ClearService();
    clearService.clear(userDao,gameDao,authDao);
    Assertions.assertEquals(userDao.userInDatabase(testUser)==false,userDao.userInDatabase(testUser)==false);
  }

  @Test
  @DisplayName("Clear Fail Test")
  void clearFail() throws DataAccessException {
    ClearService clearService = new ClearService();
    clearService.clear(userDao,gameDao,authDao);
    RegistrationService registrationService = new RegistrationService();
    registrationService.registerUser(testUser,userDao,authDao);
    Assertions.assertEquals(userDao.userInDatabase(testUser)==true,userDao.userInDatabase(testUser)==true);
  }
}