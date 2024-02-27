package model;

import java.util.UUID;

public class AuthData {
  final String authToken;
  String username=null;

  public AuthData(String authToken, String username) {
    this.authToken=authToken;
    this.username = username;
  }

  public void setUsername(String username){
    this.username = username;
  }

  public String getAuthToken() {
    return authToken;
  }


  @Override
  public String toString() {
    return authToken;
  }
}
