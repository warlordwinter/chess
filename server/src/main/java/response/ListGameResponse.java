package response;

import model.GameData;

import java.util.Collection;

public class ListGameResponse {
  final String message;

  final Collection<GameData> collection;

  public ListGameResponse(String message) {
    this.message=message;
    this.collection = null;
  }

  public ListGameResponse(Collection<GameData> collection){
    this.message = null;
    this.collection = collection;
  }
}
