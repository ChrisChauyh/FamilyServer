package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;
import requestAndResult.LoadRequest;
import requestAndResult.LoadResult;
import services.LoadService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Gson gson = new Gson();
            StringHandler stringHandler = new StringHandler();
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String url = exchange.getRequestURI().toString();
                if (url.equals("/load")) {
                    String filePath = "web" + url;
                    // Extract the JSON string from the HTTP request body

                    // Get the request body input stream
                    InputStream reqBody = exchange.getRequestBody();

                    // Read JSON string from the input stream
                    String reqData = stringHandler.readString(reqBody);

                    LoadRequest request = gson.fromJson(reqData, LoadRequest.class);
                    LoadService service = new LoadService();
                    LoadResult result = service.load(request);


                    if (result.getSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        System.out.println("Done load handler");
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                        System.out.println("Failed load handler");
                    }
                    OutputStream resBody = exchange.getResponseBody();
                    String finalJson = gson.toJson(result);
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

