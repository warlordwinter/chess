package clientTests;

import dataAccess.DataAccessException;
import dataAccess.sql.AuthSqlDataAccess;
import dataAccess.sql.GameSqlDataAccess;
import dataAccess.sql.UserSqlDataAccess;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;
import ui.requests.CreateGamesRequest;
import ui.requests.JoinGameRequest;
import ui.response.JoinGameResponse;
import ui.response.ListGameResponse;

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
        clearData();
        server = new Server();
        var port = server.run(0);
        String serverUrl = "http://localhost:" + port;
        facade = new ServerFacade(serverUrl);
        System.out.println("Started test HTTP server on " + port);
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


    @AfterAll
    static void stopServer() {
        server.stop();
    }

        @Test
        @DisplayName("Register Successful")
        void registerSuccessful() {
            clearData();
            try {
                AuthData authData=facade.register(test1UserData);
                Assertions.assertNotNull(authData, "This should not be null");
            } catch (ResponseException e) {
                fail("Exception thrown: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Register Fail")
        void registerFail() {
            Assertions.assertThrows(ResponseException.class, () -> {
                facade.register(test2UserData);
            });
        }

        @Test
        @DisplayName("Login Successful")
        void loginSuccessful() {
            try {
                clearData();
                facade.register(test1UserData);
                AuthData authData=facade.login(test1UserData);
                Assertions.assertNotNull(authData, "This should not be null");
            } catch (ResponseException e) {
                fail("Exception thrown: " + e.getMessage());
            }

        }

        @Test
        @DisplayName("Login Fail")
        void loginFail() {
            Assertions.assertThrows(ResponseException.class, () -> {
                facade.login(test2UserData);
            });
        }

        @Test
        @DisplayName("Logout Successful")
        void logoutSuccessful() {
            clearData();
            try {
                AuthData authData=facade.register(test1UserData);
                String authHeader=authData.getAuthToken();
                AuthData authData1=facade.logout(authHeader);
                Assertions.assertNotNull(authData1, "This should not be null");
            } catch (ResponseException e) {
                fail("Exception thrown: " + e.getMessage());
            }

        }

        @Test
        @DisplayName("Logout Fail")
        void logoutFail() {
            clearData();
            AuthData authData;
            try {
                authData=facade.register(test1UserData);
            } catch (ResponseException e) {
                throw new RuntimeException(e);
            }
            Assertions.assertThrows(ResponseException.class, () -> {
                facade.logout("hi! this is a fake token");
            });
        }


        @Test
        @DisplayName("Create Game Successful")
        void createGameSuccessful() {
            clearData();
            try {
                AuthData authData=facade.register(test1UserData);
                String authHeader=authData.getAuthToken();
                CreateGamesRequest createGamesRequest=new CreateGamesRequest("battle time");
                GameData gameData=facade.createGames(authHeader, createGamesRequest);
                Assertions.assertNotNull(gameData, "This should not be null");
            } catch (ResponseException e) {
                fail("Exception thrown: " + e.getMessage());
            }

        }

        @Test
        @DisplayName("Create Game Fail")
        void createGameFail() {
            clearData();
            try {
                AuthData authData=facade.register(test1UserData);
                String authHeader=authData.getAuthToken();
                CreateGamesRequest createGamesRequest=new CreateGamesRequest(null);
                Assertions.assertThrows(ResponseException.class, () -> {
                    facade.createGames(authHeader, createGamesRequest);
                });
            } catch (ResponseException e) {
                fail("Exception thrown: " + e.getMessage());
            }

        }

        @Test
        @DisplayName("List Game Successful")
        void listGameSuccessful() {
            clearData();
            try {
                AuthData authData=facade.register(test1UserData);
                String authHeader=authData.getAuthToken();
                ListGameResponse response=facade.listGames(authHeader);
                Assertions.assertNotNull(response, "This should not be null");
            } catch (ResponseException e) {
                fail("Exception thrown: " + e.getMessage());
            }

        }

        @Test
        @DisplayName("List Game Fail")
        void listGameFail() {
            clearData();
            try {
                AuthData authData=facade.register(test1UserData);
                Assertions.assertThrows(ResponseException.class, () -> {
                    facade.listGames("hihihihih");
                });
            } catch (ResponseException e) {
                fail("Exception thrown: " + e.getMessage());
            }

        }

        @Test
        @DisplayName("Join Game Successful")
        void joinGameSuccessful() {
            clearData();
            try {
                AuthData authData=facade.register(test1UserData);
                String authHeader=authData.getAuthToken();
                CreateGamesRequest createGamesRequest=new CreateGamesRequest("battle time");
                GameData gameData=facade.createGames(authHeader, createGamesRequest);

                JoinGameRequest joinGameRequest=new JoinGameRequest("BLACK", String.valueOf(gameData.getGameID()));
                JoinGameResponse joinGameResponse=facade.joinGames(authHeader, joinGameRequest);
                Assertions.assertNotNull(joinGameResponse, "This should not be null");
            } catch (ResponseException e) {
                fail("Exception thrown: " + e.getMessage());
            }

        }

        @Test
        @DisplayName("Join Game Fail")
        void joinGameFail() {
            clearData();
            try {
                AuthData authData=facade.register(test1UserData);
                String authHeader=authData.getAuthToken();
                CreateGamesRequest createGamesRequest=new CreateGamesRequest("battle time");
                GameData gameData=facade.createGames(authHeader, createGamesRequest);

                JoinGameRequest joinGameRequest=new JoinGameRequest("GREY", String.valueOf(gameData.getGameID()));

                Assertions.assertThrows(ResponseException.class, () -> {
                    facade.joinGames(authHeader, joinGameRequest);
                });
            } catch (ResponseException e) {
                fail("Exception thrown: " + e.getMessage());
            }

        }
}
