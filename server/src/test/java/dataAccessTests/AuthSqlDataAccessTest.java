package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.sql.AuthSqlDataAccess;
import model.AuthData;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuthSqlDataAccessTest {

  AuthSqlDataAccess authSqlDataAccess;

  @BeforeEach
  void setUp() {
    authSqlDataAccess = new AuthSqlDataAccess();
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    authSqlDataAccess.clearAuthData();
  }

  @Nested
  class authSQLTests {
    @Test
    @DisplayName("Clear Test")
    void clearAuthData() throws DataAccessException {
      authSqlDataAccess.clearAuthData();
    }

    @Nested
    class createAuthTokenTests {
      @Test
      @DisplayName("True Create AuthToken Test")
      void trueCreateAuthToken() {

        AuthData token;
        token=authSqlDataAccess.createAuthToken("john-tron");
        assertEquals("john-tron", token.getUsername());
      }

      @Test
      @DisplayName("False Create AuthToken Test")
      void falseCreateAuthToken() {
        AuthData token;
        token=authSqlDataAccess.createAuthToken("john-tron");
        assertNotEquals("bobby",token.getUsername());
      }
    }


    @Nested
    class testAddAuthToken {
      @Test
      @DisplayName("True Add Auth Token Test")
      void trueAddAuthToken() throws DataAccessException {
      AuthData token = authSqlDataAccess.createAuthToken("mike-the-bike");
      authSqlDataAccess.addAuthToken(token);
      String authID = token.getAuthToken();
      assertNotNull(authSqlDataAccess.getToken(authID));
      }

      @Test
      @DisplayName("True Add Auth Token Test")
      void falseAddAuthToken() throws DataAccessException {
        AuthData token = authSqlDataAccess.createAuthToken("johnny-test");
        String authID = token.getAuthToken();
        assertNull(authSqlDataAccess.getToken(authID));
      }
    }

    @Nested
    class testDeleteAuthToken {
      @Test
      @DisplayName("Successful Delete Token")
      void goodDeleteAuthToken() throws DataAccessException {
        AuthData token = authSqlDataAccess.createAuthToken("johnny-test");
        authSqlDataAccess.addAuthToken(token);
        authSqlDataAccess.deleteAuthToken(token.getAuthToken());
        assertNull(authSqlDataAccess.getToken(token.getAuthToken()));
      }

      @Test
      @DisplayName("Unsuccessful Delete Token")
      void badDeleteAuthToken() throws DataAccessException {
        AuthData token = authSqlDataAccess.createAuthToken("johnny-test");
        authSqlDataAccess.addAuthToken(token);
        authSqlDataAccess.deleteAuthToken("john-test");
        assertNotNull(authSqlDataAccess.getToken(token.getAuthToken()));
      }

    }
    @Nested
    class verifyAuthTests {
      @Test
      @DisplayName("True Verify AuthToken Test")
      void trueVerifyAuthToken() throws DataAccessException {
        AuthData token =authSqlDataAccess.createAuthToken("john-tron");

        authSqlDataAccess.addAuthToken(token);
        assertTrue(authSqlDataAccess.verifyAuthToken(token.getAuthToken()));
      }

      @Test
      @DisplayName("False Verify AuthToken Test")
      void falseVerifyAuthToken() throws DataAccessException {
        AuthData token =authSqlDataAccess.createAuthToken("john-tron");

        assertFalse(authSqlDataAccess.verifyAuthToken(token.getAuthToken()));
      }
    }
  }
}