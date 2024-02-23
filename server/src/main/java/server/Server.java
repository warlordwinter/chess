package server;

import dataAccess.MemoryUserDao;
import dataAccess.UserDao;
import server.handlers.ClearHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        UserDao userDao = new MemoryUserDao();
        Spark.staticFiles.location("web");
        Spark.delete("/db", (req,res) -> (new ClearHandler().handleRequest(req,res, userDao)));
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
