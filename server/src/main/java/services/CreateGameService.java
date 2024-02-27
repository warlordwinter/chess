package services;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.GameDao;
import exception.ResponseException;
import model.GameData;
import response.CreateGameResponse;
import spark.Request;
import spark.Response;

public class CreateGameService {
  public CreateGameResponse createGame(Request req, GameDao gameDao, AuthDao authDao) throws ResponseException {
    String authHeader = req.headers("authorization");
    if(!authDao.verifyAuthToken(authHeader)){
      throw new ResponseException(401, "Error: unauthorized");
    }
    String gameName = new Gson().fromJson(req.body(),String.class);
    GameData game = gameDao.createGame(gameName);
    return new CreateGameResponse(game.getGameID());
  }
}
