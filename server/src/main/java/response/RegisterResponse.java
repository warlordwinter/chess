package response;

public class RegisterResponse {
  final String username;
  final String authToken;
  final String message;

  public RegisterResponse(String username, String authToken) {
    this.username=username;
    this.authToken=authToken;
    this.message=null;
  }

  public RegisterResponse(String message){
    this.message = message;
    this.username = null;
    this.authToken = null;
  }

  public String getAuthToken() {
    return authToken;
  }
}
