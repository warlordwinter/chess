package services;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.GameDao;
import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;
import response.CreateGameResponse;
import response.JoinGameResponse;
import response.ListGameResponse;
import spark.Request;

import java.util.Collection;

public class GameService {

  private ResponseException authenticate(Request req, AuthDao authDao) throws ResponseException {
    String authHeader = req.headers("authorization");
    if(!authDao.verifyAuthToken(authHeader)){
      throw new ResponseException(401, "Error: unauthorized");
    }
    return null;
  }
  public CreateGameResponse createGame(Request req, GameDao gameDao, AuthDao authDao) throws ResponseException {
    authenticate(req,authDao);
    GameData gameName = new Gson().fromJson(req.body(), GameData.class);
    GameData game = gameDao.createGame(gameName.getGameName());
    return new CreateGameResponse(game.getGameID());
  }

  public ListGameResponse listGame(Request req, AuthDao authDao, GameDao gameDao) throws ResponseException {
    authenticate(req,authDao);
    Collection<GameData> collection = gameDao.listGames();
    return new ListGameResponse(collection);
  }

  public void joinGame(JoinGameRequest request, Request req, GameDao gameDao, AuthDao authDao) throws ResponseException {
    authenticate(req,authDao);

    if(request.gameID() == null||request.gameID()==""){
      throw new ResponseException(400, "Error: bad request");
    }

    if(!gameDao.checkGameAvalibility(request)){
      throw new ResponseException(403,  "Error: already taken");
    }

    gameDao.updateGame(request);

  }
}
