package serviceTests;

import dataAccess.memory.MemoryAuthDao;
import dataAccess.memory.MemoryUserDao;
import exception.ResponseException;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import response.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
  MemoryAuthDao authDao = new MemoryAuthDao();
  MemoryUserDao userDao = new MemoryUserDao();
  UserData testUser = new UserData("john","kidney","test.gmail.com");

  @Test
  @DisplayName("Login Pass Test")
  void loginAuthentication() throws ResponseException {
    LoginService loginService = new LoginService();
    userDao.addUser(testUser);
    LoginResponse response = loginService.loginAuthentication(testUser, userDao, authDao);
    assertNotNull(response.getAuthToken());
    assertEquals(testUser.getUsername(), response.getUsername());
    assertTrue(authDao.verifyAuthToken(response.getAuthToken()));
  }

  @Test
  @DisplayName("Login Fail Test")
  void loginAuthenticationFail() {
    LoginService loginService = new LoginService();
    assertThrows(ResponseException.class, () -> loginService.loginAuthentication(testUser, userDao, authDao));
  }
}