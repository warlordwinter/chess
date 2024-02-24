package model;

import java.util.UUID;

public class AuthData {
  final String authToken;

  public AuthData(String authToken) {
    this.authToken=authToken;
  }

  public String getAuthToken() {
    return authToken;
  }

}
