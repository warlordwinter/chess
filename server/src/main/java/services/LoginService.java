package services;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import response.LoginResponse;
import spark.Request;

public class LoginService {

  public LoginResponse loginAuthentication(Request req, UserDao userDao, AuthDao authDao) throws ResponseException {
    UserData user = new Gson().fromJson(req.body(),UserData.class);
    if(userDao.userInDatabase(user)) {
      throw new ResponseException(401, "Error: unauthorized");
    }
    AuthData token = authDao.createAuthToken(user.getUsername());
    authDao.addAuthToken(token);
    return new LoginResponse(user.getUsername(),token.getAuthToken());
  }
}
