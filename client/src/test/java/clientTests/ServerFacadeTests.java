package clientTests;

import dataAccess.DataAccessException;
import dataAccess.sql.AuthSqlDataAccess;
import dataAccess.sql.GameSqlDataAccess;
import dataAccess.sql.UserSqlDataAccess;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.fail;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    static GameSqlDataAccess gameSqlDataAccess = new GameSqlDataAccess();
    static AuthSqlDataAccess authSqlDataAccess = new AuthSqlDataAccess();
    static UserSqlDataAccess userSqlDataAccess = new UserSqlDataAccess();
    private UserData test1UserData = new UserData("john","cena","@gmail.com");
    private UserData test2UserData = new UserData("john","ronald",null);
    private UserData test3UserData = new UserData("already","registered","letsgo@gmail.com");


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        String serverUrl = "http://localhost:" + port;
        facade = new ServerFacade(serverUrl);
        System.out.println("Started test HTTP server on " + port);
        clearData();
//        preregister();
    }

    private static void clearData() {
        try {
            gameSqlDataAccess.clearGameData();
            userSqlDataAccess.clearUserData();
            authSqlDataAccess.clearAuthData();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void preregister(){
        try {
            facade.register(new UserData("already","registered","letsgo@gmail.com"));
        } catch (ResponseException e){
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Nested
    class registerLoginLogout{

        @Test
        @DisplayName("Register Successful")
        void registerSuccessful(){
            try{
                AuthData authData = facade.register(test1UserData);
                Assertions.assertNotNull(authData,"This should not be null");
            } catch(ResponseException e){
                fail("Exception thrown: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Register Fail")
        void registerFail(){
            Assertions.assertThrows(ResponseException.class, () -> {
                facade.register(test2UserData);
            });
        }

        @Test
        @DisplayName("Login Successful")
        void loginSuccessful(){
            try{
                facade.register(test1UserData);
                AuthData authData = facade.login(test1UserData);
                Assertions.assertNotNull(authData,"This should not be null");
            }catch(ResponseException e){
                fail("Exception thrown: " + e.getMessage());
            }

        }

        @Test
        @DisplayName("Login Fail")
        void loginFail(){
            Assertions.assertThrows(ResponseException.class, () -> {
                facade.login(test2UserData);
            });
        }

        @Test
        @DisplayName("Logout Successful")
        void logoutSuccessful(){
            try{
                AuthData authData = facade.register(test1UserData);
                String authHeader =authData.getAuthToken();
                AuthData authData1 = facade.logout(authHeader);
                Assertions.assertNotNull(authData1,"This should not be null");
            }catch(ResponseException e){
                fail("Exception thrown: " + e.getMessage());
            }

        }

        @Test
        @DisplayName("Logout Fail")
        void logoutFail() {
            try {
                AuthData authData = facade.register(test1UserData);
            } catch (ResponseException e) {
                throw new RuntimeException(e);
            }
            Assertions.assertThrows(ResponseException.class, () -> {
                facade.logout("hi! this is a fake token");
            });
        }

    }



}
