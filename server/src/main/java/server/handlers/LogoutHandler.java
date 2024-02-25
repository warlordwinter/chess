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
    AuthData authInfo = new Gson().fromJson(req.body(), AuthData.class);
    String authUser = authInfo.getAuthToken();

    try{
      LogoutService logoutService =new LogoutService().logout(authInfo,authDao);
      res.status(200);
      return new Gson().toJson(logoutService);
    } catch (ResponseException e){
      e.printStackTrace();
      res.status(e.StatusCode());
      return new Gson().toJson(e.getMessage());
    }
  }
}
