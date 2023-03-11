package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                String url = exchange.getRequestURI().toString();
                if(url.equals("/") ||  url.equals(""))
                {
                    url = "/index.html";
                }
                String filePath = "web" + url;
                File file = new File(filePath);
                if(!file.exists())
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    file = new File("web/HTML/404.html");
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                }
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(Path.of(file.getPath()), respBody);
                exchange.getResponseBody().close();
                success = true;
            }
            if(!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }

        }catch (IOException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            exchange.getResponseBody().close();


            e.printStackTrace();

        }

    }
}
