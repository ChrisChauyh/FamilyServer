package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present

                // Extract the JSON string from the HTTP request body

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);

                /**
                 *       RegisterResult request = (RegisterRequest) gson.fromJson(reqData, RegisterRequest.class);
                 *
                 *                 RegisterService service = new RegisterService();
                 *                 RegisterResult result = service.register(request);
                 *
                 *                 exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                 *                 OutputStream resBody = exchange.getResponseBody();
                 *                 gson.toJson(result, resBody);
                 *                 resBody.close();
                 */

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();
                success = true;
            }

        if (!success) {
            // The HTTP request was invalid somehow, so we return a "bad request"
            // status code to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();
        }
    }
        catch(
    IOException e)

    {
        // Some kind of internal error has occurred inside the server (not the
        // client's fault), so we return an "internal server error" status code
        // to the client.
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

        // We are not sending a response body, so close the response body
        // output stream, indicating that the response is complete.
        exchange.getResponseBody().close();

        // Display/log the stack trace
        e.printStackTrace();
    }

}
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
