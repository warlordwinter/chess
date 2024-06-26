package server.handlers;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.UserData;
import response.RegisterResponse;
import service.RegistrationService;
import spark.Request;
import spark.Response;

public class RegisterHandler {

  public Object registerUser(Request req, Response res, UserDao userDao, AuthDao authDao) {
    UserData user = new Gson().fromJson(req.body(), UserData.class);

    try {
      RegisterResponse authenticated=new RegistrationService().registerUser(user, userDao, authDao);
      res.status(200);
      return new Gson().toJson(authenticated);
    } catch(DataAccessException e){
      res.status(e.statusCode());
      RegisterResponse response = new RegisterResponse(e.getMessage());
      return new Gson().toJson(response);
    }
  }
}
