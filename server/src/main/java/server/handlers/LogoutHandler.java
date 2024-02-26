package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import services.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
  public Object handleLogout(Request req, Response res, UserDao userDao, AuthDao authDao){
    String authHeader = req.headers("authorization");

    try{
      LogoutService logoutService =new LogoutService().logout(req,authHeader,authDao);
      res.status(200);
      return "{}";
    } catch (ResponseException e){
      e.printStackTrace();
      res.status(e.StatusCode());
      return new Gson().toJson(e.getMessage());
    }
  }
}
