package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import response.RegisterResponse;
import services.RegistrationService;
import spark.Request;
import spark.Response;

public class RegisterHandler {

  public Object registerUser(Request req, Response res, UserDao userDao, AuthDao authDao) throws ResponseException {
    UserData user = new Gson().fromJson(req.body(), UserData.class);

    try {
      RegisterResponse authenticated=new RegistrationService().registerUser(user, userDao, authDao);
      res.status(200);
      return new Gson().toJson(authenticated);
    } catch(ResponseException e){
      res.status(e.StatusCode());
      RegisterResponse response = new RegisterResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }
}
