package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LoginHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

//        try {
//            // Determine the HTTP request type (GET, POST, etc.).
//            // Only allow GET requests for this operation.
//            // This operation requires a GET request, because the
//            // client is "getting" information from the server, and
//            // the operation is "read only" (i.e., does not modify the
//            // state of the server).
//            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
//
//                // Get the HTTP request headers
//                Headers reqHeaders = exchange.getRequestHeaders();
//
//                // Check to see if an "Authorization" header is present
//                if (reqHeaders.containsKey("Authorization")) {
//
//                    // Extract the auth token from the "Authorization" header
//                    String authToken = reqHeaders.getFirst("Authorization");
//
//                    // Verify that the auth token is the one we're looking for
//                    // (this is not realistic, because clients will use different
//                    // auth tokens over time, not the same one all the time).
//                    if (authToken.equals("afj232hj2332")) {
//
//                        // This is the JSON data we will return in the HTTP response body
//                        // (this is unrealistic because it always returns the same answer).
//                        String respData =
//                                "{ \"game-list\": [" +
//                                        "{ \"name\": \"fhe game\", \"player-count\": 3 }," +
//                                        "{ \"name\": \"work game\", \"player-count\": 4 }," +
//                                        "{ \"name\": \"church game\", \"player-count\": 2 }" +
//                                        "]" +
//                                        "}";
//
//                        // Start sending the HTTP response to the client, starting with
//                        // the status code and any defined headers.
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//
//                        // Now that the status code and headers have been sent to the client,
//                        // next we send the JSON data in the HTTP response body.
//
//                        // Get the response body output stream.
//                        OutputStream respBody = exchange.getResponseBody();
//
//                        // Write the JSON string to the output stream.
//                        writeString(respData, respBody);
//
//                        // Close the output stream.  This is how Java knows we are done
//                        // sending data and the response is complete.
//                        respBody.close();
//
//                        success = true;
//                    }
//                }
//            }
//
//            if (!success) {
//                // The HTTP request was invalid somehow, so we return a "bad request"
//                // status code to the client.
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                // Since the client request was invalid, they will not receive the
//                // list of games, so we close the response body output stream,
//                // indicating that the response is complete.
//                exchange.getResponseBody().close();
//            }
//        }
//        catch (IOException e) {
//            // Some kind of internal error has occurred inside the server (not the
//            // client's fault), so we return an "internal server error" status code
//            // to the client.
//            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
//            // Since the server is unable to complete the request, the client will
//            // not receive the list of games, so we close the response body output stream,
//            // indicating that the response is complete.
//            exchange.getResponseBody().close();
//
//            // Display/log the stack trace
//            e.printStackTrace();
//        }
//    }
//
//    /*
//        The writeString method shows how to write a String to an OutputStream.
//    */
//    private void writeString(String str, OutputStream os) throws IOException {
//        OutputStreamWriter sw = new OutputStreamWriter(os);
//        sw.write(str);
//        sw.flush();
    }
}