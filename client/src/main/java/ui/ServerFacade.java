package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import ui.requests.CreateGamesRequest;
import ui.requests.JoinGameRequest;
import ui.response.ListGameResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerFacade {
  private final String serverUrl;

  public ServerFacade(String url) {
    serverUrl = url;
  }

  public AuthData register(UserData userData) throws ResponseException {
    var path = "/user";
    return this.makeRequest("POST",path,userData,null,AuthData.class);
  }

  public AuthData login(UserData userData) throws ResponseException{
    var path = "/session";
    return this.makeRequest("POST",path, userData, null,AuthData.class);
  }

  public AuthData logout(String authToken) throws ResponseException {
    var path = "/session";
    return this.makeRequest("DELETE", path, null, authToken, AuthData.class);
  }

  public GameData createGames(String authHeader, CreateGamesRequest gameRequest) throws ResponseException {
    var path = "/game";
    return this.makeRequest("POST", path, gameRequest, authHeader, GameData.class);
  }

  public ListGameResponse listGames(String authHeader) throws ResponseException{
    var path = "/game";
    return this.makeRequest("GET", path, null, authHeader, ListGameResponse.class);
  }

  public void joinGames(String authHeader, JoinGameRequest joinGameRequest) throws ResponseException{
    var path = "/game";
    this.makeRequest("PUT",path,joinGameRequest,authHeader,null);
  }



  private <T> T makeRequest(String method, String path, Object request, String authToken, Class<T> responseClass) throws ResponseException {
    try {
      URL url = new URL(serverUrl + path);
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);

      if (authToken != null && !authToken.isEmpty()) {
        http.setRequestProperty("Authorization", authToken);
      }

      if (request != null) {
        writeBody(request, http);
      }

      http.connect();
      throwIfNotSuccessful(http);
      return readBody(http, responseClass);
    } catch (Exception ex) {
      throw new ResponseException(500, ex.getMessage());
    }
  }


  private static void writeBody(Object request, HttpURLConnection http) throws IOException {
    if (request != null) {
      http.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      try (OutputStream reqBody = http.getOutputStream()) {
        reqBody.write(reqData.getBytes());
      }
    }
  }

  private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
    var status = http.getResponseCode();
    if (!isSuccessful(status)) {
      throw new ResponseException(status, "failure: " + status);
    }
  }

  private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
    T response = null;
    if (http.getContentLength() < 0) {
      try (InputStream respBody = http.getInputStream()) {
        InputStreamReader reader = new InputStreamReader(respBody);
        if (responseClass != null) {
          response = new Gson().fromJson(reader, responseClass);
        }
      }
    }
    return response;
  }


  private boolean isSuccessful(int status) {
    return status / 100 == 2;
  }
}
