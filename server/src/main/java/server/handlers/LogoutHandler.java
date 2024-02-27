package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import exception.ResponseException;
import response.LoginResponse;
import serviceTests.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
  public Object handleLogout(Request req, Response res, AuthDao authDao){
    String authHeader = req.headers("authorization");

    try{
      LogoutService logoutService =new LogoutService();
      logoutService.logout(authHeader,authDao);
      res.status(200);
      return "{}";
    } catch (ResponseException e){
      e.printStackTrace();
      res.status(e.StatusCode());
      LoginResponse response =new LoginResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }
}
