package services;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.GameDao;
import exception.ResponseException;
import model.GameData;
import response.CreateGameResponse;
import response.ListGameResponse;
import spark.Request;

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

  public CreateGameResponse listGame(Request req, AuthDao authDao, GameDao gameDao) throws ResponseException {
    authenticate(req,authDao);

    return new ListGameResponse(message);
  }
}