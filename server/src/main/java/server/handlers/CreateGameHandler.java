package server.handlers;

import dataAccess.AuthDao;
import dataAccess.GameDao;
import dataAccess.UserDao;
import exception.ResponseException;
import spark.Request;
import spark.Response;

public class CreateGameHandler {


  public Object createGame(Request req, Response res, UserDao userDao, GameDao gameDao, AuthDao authDao) {

    return null;
  }
}