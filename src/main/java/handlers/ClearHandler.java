package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;
import requestAndResult.ClearResult;
import services.ClearService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {


        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String url = exchange.getRequestURI().toString();
                if (url.equals("/clear")) {

                    String filePath = "web" + url;

                    Gson gson = new Gson();
                    StringHandler stringHandler = new StringHandler();

                    ClearService service = new ClearService();
                    ClearResult result = service.clear();

                    if(result.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        System.out.println("Done clear handler");
                    }else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        System.out.println("Failed clear handler");
                    }
                    OutputStream resBody = exchange.getResponseBody();
                    String finalJson = gson.toJson(result);
                    stringHandler.writeString(finalJson,resBody);
                    resBody.close();
                }
            }
        } catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}