package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import services.RegistrationService;
import spark.Request;
import spark.Response;

public class RegisterHandler {

  public Object registerUser(Request req, Response res, UserDao userDao, AuthDao authDao) throws ResponseException {
    UserData user = new Gson().fromJson(req.body(), UserData.class);

    try {
      AuthData authenticated=new RegistrationService().registerUser(user, userDao, authDao);
      res.status(200);
      return new Gson().toJson(authenticated);
    } catch(ResponseException e){
      res.status(e.StatusCode());
      return new Gson().toJson(e.getMessage());
    }
  }
}
