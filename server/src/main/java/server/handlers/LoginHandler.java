package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import services.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {

  public Object handleLogin(Request req, Response res, UserDao userDao, AuthDao authDao) {
    try{
      LoginService loginService = new LoginService();
      AuthData token = loginService.loginAuthentication(req, userDao, authDao);
      res.status(200);
      return new Gson().toJson(token);

    }catch(ResponseException e){
      res.status(e.StatusCode());
      return new Gson().toJson(e.getMessage());
    }
  }
}
