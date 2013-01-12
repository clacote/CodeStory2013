package com.ninja_squad.codestory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import com.ninja_squad.codestory.scalaskel.ChangeComputer;
import com.ninja_squad.codestory.scalaskel.Unite;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeStoryHandler implements HttpHandler {

    public static final String UNEXPECTED = "I have no idea what you are doing";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");

        String requestMethod = exchange.getRequestMethod().toUpperCase();
        if ("GET".equals(requestMethod)) {
            doGet(exchange);
        } else if ("POST".equals(requestMethod)) {
            doPost(exchange);
        } else {
            sendResponse(exchange, UNEXPECTED, HttpURLConnection.HTTP_NOT_IMPLEMENTED);
        }
    }

    private void doGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();
        int responseCode = HttpURLConnection.HTTP_OK;

        String answer = null;

        // Try scalaskel
        if (path != null && !path.isEmpty()) {
            answer = scalaskel(path);
        }

        // Try query
        if (answer == null && query != null && !query.isEmpty()) {
            answer = answer(query);
        }

        // No answer possible
        if (answer == null) {
            answer = UNEXPECTED;
            responseCode = HttpURLConnection.HTTP_BAD_REQUEST;
        }
        sendResponse(exchange, answer, responseCode);
    }

    private void doPost(final HttpExchange exchange) throws IOException {

        InputSupplier<? extends InputStream> supplier = new InputSupplier<InputStream>() {
            @Override
            public InputStream getInput() throws IOException {
                return exchange.getRequestBody();
            }
        };
        String requestBody = CharStreams.toString(CharStreams.newReaderSupplier(supplier, Charsets.UTF_8));

        // Write received body
        System.out.println(requestBody);

        sendResponse(exchange, requestBody, HttpURLConnection.HTTP_CREATED);
    }

    private void sendResponse(HttpExchange exchange, final String body, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, 0);
        OutputStream responseBody = exchange.getResponseBody();
        PrintWriter responseWriter = new PrintWriter(responseBody, true);
        responseWriter.print(body);
        responseWriter.close();
        responseBody.close();
    }

    /**
     * Answer given query.
     *
     * @param query HTTP query string
     * @return Answer, null if unknown question.
     */
    @VisibleForTesting
    protected String answer(final String query) {

        String answer = null;
        if (query != null) {

            System.out.println(query);

            // Try predefined answer
            answer = STATIC_ANSWERS.get(query);

            if (answer == null) {
                // Try to identify numerical computation
                answer = calculate(query);
            }
        }
        return answer;
    }

    private static final String SCALASKEL_PATH = "/scalaskel/change/";

    private String scalaskel(String path) {
        String result = null;
        if (path.startsWith(SCALASKEL_PATH)) {
            String strValue = path.substring(SCALASKEL_PATH.length(), path.length());
            if (!strValue.isEmpty()) {
                int value = Integer.valueOf(strValue);
                Set<Map<Unite, Integer>> change = ChangeComputer.getInstance().change(value);
                result = JSON.toJson(change);
            }
        }
        return result;
    }

    private static final Map<String, String> STATIC_ANSWERS = ImmutableMap.<String, String>builder()
            .put("q=Quelle+est+ton+adresse+email", "cyril@ninja-squad.com")
            .put("q=Es+tu+abonne+a+la+mailing+list(OUI/NON)", "OUI")
            .put("q=Es+tu+heureux+de+participer(OUI/NON)", "OUI")
            .put("q=Es+tu+pret+a+recevoir+une+enonce+au+format+markdown+par+http+post(OUI/NON)", "OUI")
            .put("q=Est+ce+que+tu+reponds+toujours+oui(OUI/NON)", "NON")
            .put("q=As+tu+bien+recu+le+premier+enonce(OUI/NON)", "OUI")
            .build();

    private final static Pattern CALCULATION = Pattern.compile("q=(-?\\d+)([\\+\\-\\*/])(-?\\d+)");

    private String calculate(final String query) {
        String result = null;
        Matcher matcher = CALCULATION.matcher(query);
        if (matcher.matches()) {
            long a = Long.valueOf(matcher.group(1));
            String operator = matcher.group(2);
            long b = Long.valueOf(matcher.group(3));

            if (operator != null) {
                Long lResult = calculate(a, operator.charAt(0), b);
                if (lResult != null) {
                    result = Long.toString(lResult);
                }
            }
        }
        return result;
    }

    /**
     * Compute numerical operation between a and b, if operator is '+', '-', '*' or '/';
     *
     * @return mathematical result, or null if uncomputable
     */
    private Long calculate(long a, char operator, long b) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
        }
        return null;
    }
}
