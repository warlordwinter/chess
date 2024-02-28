package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.GameDao;
import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;
import response.CreateGameResponse;
import response.JoinGameResponse;
import response.ListGameResponse;
import serviceTests.GameService;
import spark.Request;
import spark.Response;

public class GameHandler {


  public Object createGame(Request req, Response res, GameDao gameDao, AuthDao authDao) {
    try{
      String token = req.headers("authorization");
      GameData gameName = new Gson().fromJson(req.body(), GameData.class);
      CreateGameResponse response = new GameService().createGame(token,gameName,gameDao,authDao);
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
      String token = req.headers("authorization");
      ListGameResponse response=new GameService().listGame(token, authDao, gameDao);
      res.status(200);
      return new Gson().toJson(response);
    } catch(ResponseException e){
      res.status(e.StatusCode());
      ListGameResponse response = new ListGameResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }

  public Object joinGame(Request req, Response res, GameDao gameDao, AuthDao authDao) {
    try{
      String token = req.headers("authorization");
      JoinGameRequest request = new Gson().fromJson(req.body(), JoinGameRequest.class);
      GameService response = new GameService();
      response.joinGame(request,token,gameDao,authDao);
      res.status(200);
      return "{}";
    }catch(ResponseException e){
      res.status(e.StatusCode());
      JoinGameResponse response = new JoinGameResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }
}