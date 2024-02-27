package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;
import exception.ResponseException;
import response.CreateGameResponse;
import response.LoginResponse;
import services.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {


  public Object createGame(Request req, Response res, GameDao gameDao, AuthDao authDao) {
    try{
      CreateGameResponse response = new CreateGameService().createGame(req,gameDao,authDao);
      res.status(200);
      return new Gson().toJson(response);
    }catch(ResponseException e) {
      res.status(e.StatusCode());
      res.status(e.StatusCode());
      LoginResponse response = new LoginResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }
}