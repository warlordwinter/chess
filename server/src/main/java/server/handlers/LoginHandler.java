package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.UserDao;
import exception.ResponseException;
import response.LoginResponse;
import serviceTests.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {

  public Object handleLogin(Request req, Response res, UserDao userDao, AuthDao authDao) {
    try{
      LoginResponse token = new LoginService().loginAuthentication(req, userDao, authDao);
      res.status(200);
      return new Gson().toJson(token);

    }catch(ResponseException e){
      res.status(e.StatusCode());
//      ResponseObject
      res.status(e.StatusCode());
      LoginResponse response = new LoginResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }
}
