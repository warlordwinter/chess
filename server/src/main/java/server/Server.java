package server;

import dataAccess.*;
import server.handlers.*;
import spark.*;

public class Server {
    UserDao userDao = new MemoryUserDao();
    GameDao gameDao = new MemoryGameDao();
    AuthDao authDao = new MemoryAuthDao();


    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");
        Spark.get("/gane" , (req,res) -> (new GameHandler().listGames(req,res,authDao,gameDao)));
        Spark.delete("/session", (req,res) -> (new LogoutHandler().handleLogout(req,res,authDao)));
        Spark.post("/session", (req,res) -> (new LoginHandler().handleLogin(req,res,userDao,authDao)));
        Spark.delete("/db", (req,res) -> (new ClearHandler().handleRequest(res, userDao,gameDao,authDao)));
        Spark.post("/user" ,(req,res) -> (new RegisterHandler().registerUser(req,res,userDao,authDao)));
        Spark.post("/game", (req,res) -> (new GameHandler().createGame(req,res,gameDao,authDao)));
        // Register your endpoints and handle exceptions here.
        Spark.init();
        Spark.awaitInitialization();
        return Spark.port();
    }



    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
