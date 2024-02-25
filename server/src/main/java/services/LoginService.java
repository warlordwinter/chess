package services;

import com.google.gson.Gson;
import dataAccess.AuthDao;
import dataAccess.UserDao;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import spark.Request;

public class LoginService {

  public AuthData loginAuthentication(Request req,UserDao userDao, AuthDao authDao) throws ResponseException {
    UserData user = new Gson().fromJson(req.body(),UserData.class);
    userDao.getUser(user.getUsername());
    if(user.getUsername() == null) {
      throw new ResponseException(401, "Error: unauthorized");
    }
    AuthData token = authDao.createAuthToken();
    authDao.addAuthToken(user.getUsername(),token);
    return token;
  }
}
