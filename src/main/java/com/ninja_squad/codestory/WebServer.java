package com.ninja_squad.codestory;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebServer {

    public static void main(String[] args) throws IOException {

        final int port = getPort();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new CodeStoryHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.printf("Server is listening on port %d\n", port);
    }

    public static final int DEFAULT_PORT = 8080;
    private static int getPort() {
        String envPort = System.getenv("PORT");
        return envPort != null ? Integer.valueOf(envPort) : DEFAULT_PORT;

    }
}
