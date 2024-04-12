package server;

import dataAccess.*;
import dataAccess.sql.AuthSqlDataAccess;
import dataAccess.sql.GameSqlDataAccess;
import dataAccess.sql.UserSqlDataAccess;
import server.handlers.*;
import server.websocket.WebSocketHandler;
import spark.Spark;

public class Server {

    public Server(){
        try {
            DatabaseManager.configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    UserDao userDao = new UserSqlDataAccess();
    GameDao gameDao = new GameSqlDataAccess();
    AuthDao authDao = new AuthSqlDataAccess();
    WebSocketHandler webSocketHandler = new WebSocketHandler(userDao, gameDao, authDao);


    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        Spark.webSocket("/connect",webSocketHandler);

        Spark.get("/game" , (req,res) -> (new GameHandler().listGames(req,res,authDao,gameDao)));
        Spark.delete("/session", (req,res) -> (new LogoutHandler().handleLogout(req,res,authDao)));
        Spark.post("/session", (req,res) -> (new LoginHandler().handleLogin(req,res,userDao,authDao)));
        Spark.delete("/db", (req,res) -> (new ClearHandler().handleRequest(res, userDao,gameDao,authDao)));
        Spark.post("/user" ,(req,res) -> (new RegisterHandler().registerUser(req,res,userDao,authDao)));
        Spark.post("/game", (req,res) -> (new GameHandler().createGame(req,res,gameDao,authDao)));
        Spark.put("/game", (req,res)->(new GameHandler().joinGame(req,res,gameDao,authDao)));
        Spark.init();
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
