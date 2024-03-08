package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.UserData;
import response.LoginResponse;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {

  public Object handleLogin(Request req, Response res, UserDao userDao, AuthDao authDao) {
    try{
      UserData user = new Gson().fromJson(req.body(),UserData.class);
      LoginResponse token = new LoginService().loginAuthentication(user, userDao, authDao);
      res.status(200);
      return new Gson().toJson(token);

    }catch(DataAccessException e){
      res.status(e.getStatusCode());
//      ResponseObject
      res.status(e.getStatusCode());
      LoginResponse response = new LoginResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }
}
