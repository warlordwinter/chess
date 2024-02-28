package response;

public class LoginResponse {
  final String username;
  final String authToken;
  final String message;

  public LoginResponse(String username, String authToken) {
    this.username=username;
    this.authToken=authToken;
    this.message = null;
  }

  public LoginResponse(String message){
    this.message = message;
    this.username = null;
    this.authToken= null;
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getUsername() {
    return username;
  }
}
