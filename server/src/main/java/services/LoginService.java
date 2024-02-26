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
//    UserData userRequest = userDao.getUser(user.getUsername(),user.getPassword());
    if(user.getUsername() == null ||user.getPassword() == null || user.getEmail() ==null) {
      throw new ResponseException(401, "Error: unauthorized");
    }
    AuthData token = authDao.createAuthToken(user.getUsername());
    authDao.addAuthToken(token);
    return token;
  }
}
