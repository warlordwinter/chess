package model;

import chess.ChessGame;

import java.util.UUID;

public class GameData {
  final Integer gameID;
  final String whiteUsername;
  final String blackUsername;
  final String gameName;
  final ChessGame game;

  public GameData(Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    this.gameID=gameID;
    this.whiteUsername=whiteUsername;
    this.blackUsername=blackUsername;
    this.gameName=gameName;
    this.game=game;
  }

  public GameData(String gameName,Integer gameID){
    this.gameID = gameID;
    this.whiteUsername="";
    this.blackUsername="";
    this.gameName=gameName;
    this.game=null;
  }


  public Integer getGameID() {
    return gameID;
  }

  public String getWhiteUsername() {
    return whiteUsername;
  }

  public String getBlackUsername() {
    return blackUsername;
  }

  public String getGameName() {
    return gameName;
  }

  public ChessGame getGame() {
    return game;
  }
}
