package server;

import com.google.gson.Gson;
import dataAccess.*;
import exception.ResponseException;
import model.UserData;
import server.handlers.ClearHandler;
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
        Spark.delete("/db", (req,res) -> (new ClearHandler().handleRequest(req,res, userDao,gameDao,authDao)));
        Spark.post("/user" ,(req,res) ->(new RegisterHandler().registerUser(req,res,userDao,authDao)));
        // Register your endpoints and handle exceptions here.
        Spark.init();
        Spark.awaitInitialization();
        return Spark.port();
    }


    public String clear(Request rec, Response res){
        System.out.println("hello");
        return null;
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
