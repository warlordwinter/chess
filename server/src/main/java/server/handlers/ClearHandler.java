package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;
import exception.ResponseException;
import services.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {

  public Object handleRequest(Response res, UserDao userDao, GameDao gameDao, AuthDao authDao) {
    try {
      ClearService clearService = new ClearService();
      clearService.clear(userDao, gameDao, authDao);
      res.status(200);
      return new Gson().toJson(clearService);
    } catch (ResponseException e) {
      e.printStackTrace();  // Log the exception
      res.status(e.StatusCode());
      return "{}";
    }
  }
}
