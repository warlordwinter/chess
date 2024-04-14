package model;

public class AuthData {
  final String authToken;
  String username=null;

  public AuthData(String authToken, String username) {
    this.authToken=authToken;
    this.username = username;
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public String toString() {
    return authToken;
  }
}
