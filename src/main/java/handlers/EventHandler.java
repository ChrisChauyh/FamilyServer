package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;
import requestAndResult.EventIDResult;
import requestAndResult.EventResult;
import services.EventIDService;
import services.EventService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Gson gson = new Gson();
            StringHandler stringHandler = new StringHandler();
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String url = exchange.getRequestURI().toString();
                if (url.equals("/event")) {
                    // Get the HTTP request headers
                    Headers reqHeaders = exchange.getRequestHeaders();
                    // Check to see if an "Authorization" header is present
                    if (reqHeaders.containsKey("Authorization")) {
                        // Extract the auth token from the "Authorization" header
                        String authToken = reqHeaders.getFirst("Authorization");
                        EventService service = new EventService();
                        EventResult result = service.load(authToken);
                        if (result.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            System.out.println("Done event handler");
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                            System.out.println("Failed event handler");
                        }
                        OutputStream resBody = exchange.getResponseBody();
                        String finalJson = gson.toJson(result);
                        stringHandler.writeString(finalJson, resBody);
                        resBody.close();
                    }
                } else {
                    //single
                    // Get the HTTP request headers
                    Headers reqHeaders = exchange.getRequestHeaders();
                    // Check to see if an "Authorization" header is present
                    if (reqHeaders.containsKey("Authorization")) {
                        // Extract the auth token from the "Authorization" header
                        String authToken = reqHeaders.getFirst("Authorization");
                        String[] urlArray = url.split("/");
                        String eventID = urlArray[2];
                        EventIDService service = new EventIDService();
                        EventIDResult result = service.load(authToken,eventID);
                        if (result.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            System.out.println("Done eventID handler");
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                            System.out.println("Failed eventID handler");
                        }
                        OutputStream resBody = exchange.getResponseBody();
                        String finalJson = gson.toJson(result);
                        stringHandler.writeString(finalJson, resBody);
                        resBody.close();
                    }
                }
            }
        } catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

