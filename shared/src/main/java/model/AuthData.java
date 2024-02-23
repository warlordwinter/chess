package model;

public class AuthData {
  final String authToken;
  final String userName;

  public AuthData(String authToken, String userName) {
    this.authToken=authToken;
    this.userName = userName;
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getUserName() {
    return userName;
  }
}
