package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.sql.UserSqlDataAccess;
import exception.ResponseException;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSqlDataAccessTest {
  private static UserSqlDataAccess userSqlDataAccess;
  private UserData testUser,badTestUser;

  @BeforeEach
  void setUp() throws DataAccessException {
    DatabaseManager.configureDatabase();
    userSqlDataAccess = new UserSqlDataAccess();
    badTestUser=new UserData("notInTable", "notInTable", "notInTable@gmail.com");
    testUser=new UserData("testUser", "testPassword", "test@example.com");
  }

  @Nested
  class addUserTests {
    @Test
    @DisplayName("Successful Add User Test")
    public void trueTestAddUser() {

      try {
        userSqlDataAccess.addUser(testUser);

        assertEquals(testUser, userSqlDataAccess.getUser(testUser));

      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }

    @Test
    @DisplayName("Failed Add User Test")
    public void falseTestAddUser() {
      UserData badTestUser=new UserData("notInTable", "notInTable", "notInTable@gmail.com");

      try {
        assertNull(userSqlDataAccess.getUser(badTestUser));

      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }
  }

  @Nested
  class getUserTests{

    @Test
    @DisplayName("Successful Get User Test")
    public void trueTestGetUser(){
      try {
        userSqlDataAccess.addUser(testUser);
        assertEquals(testUser, userSqlDataAccess.getUser(testUser));
      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }

    @Test
    @DisplayName("Failed Get User Test")
    public void falseTestGetUser(){
      try {
        userSqlDataAccess.addUser(testUser);
        assertNull(userSqlDataAccess.getUser(badTestUser));
      } catch (DataAccessException e) {
        fail("Exception thrown: " + e.getMessage());
      }
    }
  }

  @Nested
  class userInDataBaseTest{

    @Test
    @DisplayName("Successful User in Database Test")
    void trueUserInDataBaseTest(){
      try {
        userSqlDataAccess.addUser(testUser);
        userSqlDataAccess.userInDatabase(testUser);
      } catch (DataAccessException e){
        fail("Exception thrown: " + e.getMessage());
      }
    }

    @Test
    @DisplayName("Failed User in Database Test")
    void falseUserInDataBaseTest(){
      try {
        userSqlDataAccess.userInDatabase(badTestUser);
      } catch (DataAccessException e){
        fail("Exception thrown: " + e.getMessage());
      }
    }
  }

}