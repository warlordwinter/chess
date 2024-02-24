package server.handlers;

import com.google.gson.Gson;
import dataAccess.GameDao;
import dataAccess.UserDao;
import exception.ResponseException;
import spark.Request;
import spark.Response;

public class LoginHandler {

  public Object handleLogin(Request req, Response res, UserDao userDao, GameDao gameDao) {
    try{


    }catch(ResponseException e){
      return new Gson().toJson(e.getMessage());
    }
  }
}
