package com.ninja_squad.codestory;

import com.google.common.annotations.VisibleForTesting;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class CodeStoryHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        OutputStream responseBody = exchange.getResponseBody();
        if (requestMethod.equalsIgnoreCase("GET")) {

            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");

            String query = getQuery(exchange);
            String answer = answer(query);
            int responseCode = HttpURLConnection.HTTP_OK;

            if (answer == null) {
                answer = "I have no idea what you are doing";
                responseCode = HttpURLConnection.HTTP_BAD_REQUEST;
            }

            exchange.sendResponseHeaders(responseCode, 0);
            PrintWriter responseWriter = new PrintWriter(responseBody, true);
            responseWriter.print(answer);
            responseWriter.close();
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
        }
        responseBody.close();
    }

    private String getQuery(HttpExchange exchange) {
        return exchange.getRequestURI().getQuery();
    }

    /**
     * Answer given query.
     * @param query HTTP query string
     * @return Answer, null if unknown question.
     */
    @VisibleForTesting
    protected String answer(final String query) {
        return ANSWERS.get(query);
    }

    private static final Map<String, String> ANSWERS = new HashMap<String, String>();
    static {
        ANSWERS.put("q=Quelle+est+ton+adresse+email", "cyril@ninja-squad.com");
        ANSWERS.put("q=Es+tu+abonne+a+la+mailing+list(OUI/NON)", "OUI");
    }
}
