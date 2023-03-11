package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;
import requestAndResult.FillResult;
import services.FillService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FillHandler implements HttpHandler {
    private int gens;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Gson gson = new Gson();
            StringHandler stringHandler = new StringHandler();
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                String url = exchange.getRequestURI().toString();
                List<String> tokens = Arrays.stream(url.split("/"))
                        .filter(token -> !token.isEmpty())
                        .collect(Collectors.toList());

                //defult 0 to 4 or add generations
                if(tokens.size()<3)
                {
                    gens = 4;
                }else{
                    gens = Integer.parseInt(tokens.get(2));
                }

                FillService service = new FillService();
                FillResult result = service.fill(tokens.get(1),gens);

                if (result.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    System.out.println("Done fill handler");
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    System.out.println("Failed fill handler");
                }
                OutputStream resBody = exchange.getResponseBody();
                String finalJson = gson.toJson(result);
                stringHandler.writeString(finalJson, resBody);
                resBody.close();
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
