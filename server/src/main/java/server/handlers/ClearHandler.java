package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import dataAccess.UserDao;
import service.ClearService;
import spark.Response;

public class ClearHandler {

  public Object handleRequest(Response res, UserDao userDao, GameDao gameDao, AuthDao authDao) {
    try {
      ClearService clearService = new ClearService();
      clearService.clear(userDao, gameDao, authDao);
      res.status(200);
      return new Gson().toJson(clearService);
    } catch (DataAccessException e) {
      e.printStackTrace();  // Log the exception
      res.status(e.statusCode());
      return "{}";
    }
  }
}
