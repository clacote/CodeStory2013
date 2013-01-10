package com.ninja_squad.codestory;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

public class CodeStoryHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        OutputStream responseBody = exchange.getResponseBody();
        if (requestMethod.equalsIgnoreCase("GET")) {

            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            PrintWriter responseWriter = new PrintWriter(responseBody, true);
            responseWriter.print("cyril+codestory@ninja-squad.com");
            responseWriter.close();
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
        }
        responseBody.close();
    }

    private static final String PARAMETER = "q";
    private static final String QUERY = "Quelle+est+ton+adresse+email";
}
