package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;
import requestAndResult.RegisterRequest;
import requestAndResult.RegisterResult;
import services.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Gson gson = new Gson();
            StringHandler stringHandler = new StringHandler();
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                String url = exchange.getRequestURI().toString();
                if (url.equals("/user/register")) {
                    // Get the request body input stream
                    InputStream reqBody = exchange.getRequestBody();
                    // Read JSON string from the input stream
                    String reqData = stringHandler.readString(reqBody);
                    //Creates a new user account (user row in the database)
                    // with register request
                    RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);
                    RegisterService service = new RegisterService();
                    RegisterResult registerResult = service.register(request);


                    if (registerResult.getSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        System.out.println("Done personID handler");
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                        System.out.println("Failed personID handler");
                    }
                    OutputStream resBody = exchange.getResponseBody();
                    String finalJson = gson.toJson(registerResult);
                    stringHandler.writeString(finalJson, resBody);
                    resBody.close();
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
