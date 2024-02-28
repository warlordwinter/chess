package serviceTests;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.GameDao;
import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;
import response.CreateGameResponse;
import response.ListGameResponse;
import spark.Request;

import java.util.Collection;

public class GameService {

  private ResponseException authenticate(String token, AuthDao authDao) throws ResponseException {
    if(!authDao.verifyAuthToken(token)){
      throw new ResponseException(401, "Error: unauthorized");
    }
    return null;
  }
  public CreateGameResponse createGame(String token,GameData gameData, GameDao gameDao, AuthDao authDao) throws ResponseException {
    authenticate(token,authDao);
    GameData game = gameDao.createGame(gameData.getGameName());
    return new CreateGameResponse(game.getGameID());
  }

  public ListGameResponse listGame(String token, AuthDao authDao, GameDao gameDao) throws ResponseException {
    authenticate(token,authDao);
    Collection<GameData> collection = gameDao.listGames();
    return new ListGameResponse(collection);
  }

  public void joinGame(JoinGameRequest request, String token, GameDao gameDao, AuthDao authDao) throws ResponseException {
    authenticate(token,authDao);

    if(request.gameID() == null||request.gameID()==null){
      throw new ResponseException(400, "Error: bad request");
    }

    String authHeader = token;
    gameDao.updateGame(request,authDao,authHeader);
  }
}
