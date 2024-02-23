package server.handlers;

import dataAccess.UserDao;
import services.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {

  public Object handleRequest(Request req, Response res, UserDao userDao) {
    ClearService clearService = new ClearService();
    clearService.clear(userDao);
    return "{}";
  }
}
