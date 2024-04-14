package ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import ui.requests.CreateGamesRequest;
import ui.requests.JoinGameRequest;
import ui.response.JoinGameResponse;

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

  public JoinGameResponse joinGames(String authHeader, JoinGameRequest joinGameRequest) throws ResponseException{
    var path = "/game";
    return this.makeRequest("PUT",path,joinGameRequest,authHeader, JoinGameResponse.class);
  }

  public JoinGameResponse observe(String authHeader,JoinGameRequest joinGameRequest) throws ResponseException {
    var path = "/game";
    return this.makeRequest("PUT",path,joinGameRequest,authHeader, JoinGameResponse.class);
  }

  private <T> T makeRequest(String method, String path, Object request, String authToken, Class<T> responseClass) throws ResponseException {
    try {
      URL url = new URL(serverUrl + path);
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);

      if (authToken != null && !authToken.isEmpty()) {
        http.setRequestProperty("authorization", authToken);
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
      try {
        String reqData = new Gson().toJson(request);
        if (reqData != null && !reqData.isEmpty()) {
          try (OutputStream reqBody = http.getOutputStream()) {
            reqBody.write(reqData.getBytes());
          }
        } else {
          System.err.println("Request body is empty after serialization.");
        }
      } catch (JsonSyntaxException e) {
        System.err.println("Failed to serialize request object to JSON: " + e.getMessage());
      } catch (Exception e) {
        System.err.println("Error writing request data to output stream: " + e.getMessage());
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
