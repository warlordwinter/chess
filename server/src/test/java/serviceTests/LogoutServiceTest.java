package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.memory.MemoryAuthDao;
import dataAccess.memory.MemoryUserDao;
import exception.ResponseException;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.LogoutService;
import service.RegistrationService;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {
  MemoryAuthDao authDao = new MemoryAuthDao();
  MemoryUserDao userDao = new MemoryUserDao();
  UserData testUser = new UserData("john","kidney","test@gmail.com");
  String token = null;

  @BeforeEach
  void setUp() throws DataAccessException {
    RegistrationService registrationService = new RegistrationService();
    token = registrationService.registerUser(testUser,userDao,authDao).getAuthToken();
  }

  @Test
  @DisplayName("Logout Fail Test")
  void logoutFail() {
    LogoutService logoutService = new LogoutService();
    String invalidToken = "invalidToken";
    assertThrows(ResponseException.class, () -> logoutService.logout(invalidToken, authDao));
  }
  @Test
  @DisplayName("Logout Pass Test")
  void logoutSuccessful() throws DataAccessException {
    LogoutService logoutService = new LogoutService();
    logoutService.logout(token, authDao);
    assertFalse(authDao.verifyAuthToken(token));
  }

}