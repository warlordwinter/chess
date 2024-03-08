package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.memory.MemoryAuthDao;
import dataAccess.memory.MemoryUserDao;
import exception.ResponseException;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.RegistrationService;

class RegistrationServiceTest {
  MemoryAuthDao authDao = new MemoryAuthDao();
  MemoryUserDao userDao = new MemoryUserDao();
  UserData testUser = new UserData("john","kidney","test.gmail.com");

  @Test
  @DisplayName("Register User Pass")
  void registerUserPass() throws DataAccessException {
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