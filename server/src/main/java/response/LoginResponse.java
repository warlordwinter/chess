package response;

public class LoginResponse {
  final String username;
  final String authtoken;
  final String message;

  public LoginResponse(String username, String authtoken) {
    this.username=username;
    this.authtoken=authtoken;
    this.message = null;
  }

  public LoginResponse(String message){
    this.message = message;
    this.username = null;
    this.authtoken = null;
  }
}
