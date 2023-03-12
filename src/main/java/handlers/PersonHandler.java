package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;
import requestAndResult.PersonIDResult;
import requestAndResult.PersonResult;
import services.PersonIDService;
import services.PersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Gson gson = new Gson();
            StringHandler stringHandler = new StringHandler();
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String url = exchange.getRequestURI().toString();
                if (url.equals("/person")) {
                    // Get the HTTP request headers
                    Headers reqHeaders = exchange.getRequestHeaders();
                    // Check to see if an "Authorization" header is present
                    if (reqHeaders.containsKey("Authorization")) {
                        // Extract the auth token from the "Authorization" header
                        String authToken = reqHeaders.getFirst("Authorization");
                        PersonService service = new PersonService();
                        PersonResult result = service.load(authToken);

                        if (result.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            System.out.println("Done person handler");
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            System.out.println("Failed person handler");
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
                        String personID = urlArray[2];
                        PersonIDService service = new PersonIDService();
                        PersonIDResult result = service.load(authToken,personID);

                        if (result.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            System.out.println("Done person handler");
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            System.out.println("Failed person handler");
                        }
                        OutputStream resBody = exchange.getResponseBody();
                        String finalJson = gson.toJson(result);
                        stringHandler.writeString(finalJson, resBody);
                        resBody.close();
                    }
                }
            }
        } catch (IOException | DataAccessException| RuntimeException | SQLException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}

