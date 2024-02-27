package response;

public class CreateGameResponse {
  final Integer gameID;
  final String message;

  public CreateGameResponse(Integer gameID) {
    this.gameID=gameID;
    this.message = null;
  }

  public CreateGameResponse(String message){
    this.gameID = null;
    this.message = message;
  }

}
