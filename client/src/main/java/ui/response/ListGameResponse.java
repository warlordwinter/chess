package ui.response;

import model.GameData;

import java.util.Collection;

public class ListGameResponse {
  final String message;

  final Collection<GameData> games;

  public ListGameResponse(String message) {
    this.message=message;
    this.games = null;
  }

  public ListGameResponse(Collection<GameData> games){
    this.message = null;
    this.games = games;
  }

  public Collection<GameData> getGames() {
    return games;
  }
}
