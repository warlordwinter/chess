package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.GameDao;
import exception.ResponseException;
import response.CreateGameResponse;
import response.ListGameResponse;
import services.GameService;
import spark.Request;
import spark.Response;

public class GameHandler {


  public Object createGame(Request req, Response res, GameDao gameDao, AuthDao authDao) {
    try{
      CreateGameResponse response = new GameService().createGame(req,gameDao,authDao);
      res.status(200);
      return new Gson().toJson(response);
    }catch(ResponseException e) {
      res.status(e.StatusCode());
      CreateGameResponse response = new CreateGameResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }

  public Object listGames(Request req, Response res, AuthDao authDao, GameDao gameDao) {
    try {
      CreateGameResponse response=new GameService().listGame(req, authDao, gameDao);
      res.status(200);
      return new Gson().toJson(response);
    } catch(ResponseException e){
      res.status(e.StatusCode());
      ListGameResponse response = new ListGameResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }
}