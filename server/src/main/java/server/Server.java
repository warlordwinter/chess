package server;

import dataAccess.*;
import server.handlers.ClearHandler;
import server.handlers.LoginHandler;
import server.handlers.RegisterHandler;
import services.RegistrationService;
import spark.*;

public class Server {
    RegistrationService registerService = new RegistrationService();
    UserDao userDao = new MemoryUserDao();
    GameDao gameDao = new MemoryGameDao();
    AuthDao authDao = new MemoryAuthDao();


    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");
        Spark.post("/session", (req,res) -> (new LoginHandler().handleLogin(req,res,userDao,gameDao)));
        Spark.delete("/db", (req,res) -> (new ClearHandler().handleRequest(res, userDao,gameDao,authDao)));
        Spark.post("/user" ,(req,res) ->(new RegisterHandler().registerUser(req,res,userDao,authDao)));
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
