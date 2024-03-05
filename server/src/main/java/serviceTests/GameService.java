package serviceTests;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.GameDao;
import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;
import response.CreateGameResponse;
import response.ListGameResponse;
import spark.Request;

import java.util.Collection;

public class GameService {

  private ResponseException authenticate(String token, AuthDao authDao) throws DataAccessException {
    if(!authDao.verifyAuthToken(token)){
      throw new DataAccessException(401, "Error: unauthorized");
    }
    return null;
  }
  public CreateGameResponse createGame(String token,GameData gameData, GameDao gameDao, AuthDao authDao) throws DataAccessException {
    authenticate(token,authDao);
    GameData game = gameDao.createGame(gameData.getGameName());
    return new CreateGameResponse(game.getGameID());
  }

  public ListGameResponse listGame(String token, AuthDao authDao, GameDao gameDao) throws DataAccessException {
    authenticate(token,authDao);
    Collection<GameData> collection = gameDao.listGames();
    return new ListGameResponse(collection);
  }

  public void joinGame(JoinGameRequest request, String token, GameDao gameDao, AuthDao authDao) throws DataAccessException {
    authenticate(token,authDao);

    if(request.gameID() == null||request.gameID()==null){
      throw new DataAccessException(400, "Error: bad request");
    }

    String authHeader = token;
    gameDao.updateGame(request,authDao,authHeader);
  }
}
