package server;

import dataAccess.*;
import server.handlers.ClearHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        UserDao userDao = new MemoryUserDao();
        GameDao gameDao = new MemoryGameDao();
        AuthDao authDao = new MemoryAuthDao();
        Spark.staticFiles.location("web");
        Spark.delete("/db", (req,res) -> (new ClearHandler().handleRequest(req,res, userDao,gameDao,authDao)));
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
