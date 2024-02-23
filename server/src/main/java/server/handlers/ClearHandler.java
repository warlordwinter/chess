package server.handlers;

import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;
import services.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {

  public Object handleRequest(Request req, Response res, UserDao userDao, GameDao gameDao, AuthDao authDao) {
    ClearService clearService = new ClearService();
    clearService.clear(userDao,gameDao,authDao);
    return "{}";
  }
}
