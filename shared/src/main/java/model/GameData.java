package model;

import chess.ChessGame;

public class GameData {
  final Integer gameID;
  String whiteUsername;
  String blackUsername;
  final String gameName;
  final ChessGame game;

  public GameData(Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    this.gameID=gameID;
    this.whiteUsername=whiteUsername;
    this.blackUsername=blackUsername;
    this.gameName=gameName;
    this.game=game;
  }

  public GameData(String gameName,Integer gameID,ChessGame game){
    this.gameID = gameID;
    this.whiteUsername=null;
    this.blackUsername=null;
    this.gameName=gameName;
    this.game=game;
  }

  public GameData(String gameName,Integer gameID){
    this.gameID = gameID;
    this.whiteUsername=null;
    this.blackUsername=null;
    this.gameName=gameName;
    this.game=new ChessGame();
  }

  public GameData(Integer gameID){
    this.gameID = gameID;
    this.whiteUsername=null;
    this.blackUsername=null;
    this.gameName=null;
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

  public void setBlackUsername(String blackUsername){
    this.blackUsername = blackUsername;
  }

  public void setWhiteUsername(String whiteUsername){
    this.whiteUsername = whiteUsername;
  }
}
