package ui.requests;

public class CreateGamesRequest {
  private String gameName;

  public CreateGamesRequest(String gameName) {
    this.gameName = gameName;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }
}
