package ui.websocket;

import com.google.gson.Gson;
import exception.ResponseException;
import org.glassfish.grizzly.http.server.Session;
import org.glassfish.tyrus.core.wsadl.model.Endpoint;

import javax.management.Notification;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.MessageHandler;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

  Session session;
  NotificationHandler notificationHandeler;


  public WebSocketFacade(String serverUrl, NotificationHandler notificationHandler) throws ResponseException {
    try {
      serverUrl=serverUrl.replace("http", "ws");
      URI socketURI=new URI(serverUrl + "/connect");
      this.notificationHandeler=notificationHandler;

      WebSocketContainer container =ContainerProvider.getWebSocketContainer();
      this.session =(Session) container.connectToServer(this,socketURI);
      //deserialize the server message similar to websockethandler

      this.session.addMessageHandler(new MessageHandler.Whole<String>() {
        @Override
        public void onMessage(String message) {
          Notification notification = new Gson().fromJson(message, Notification.class);
          notificationHandler.notify(notification);
        }
      });



    } catch (DeploymentException | IOException | URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }
}
