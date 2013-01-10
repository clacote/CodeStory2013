package com.ninja_squad.codestory;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebServer {

    private HttpServer server;

    public static void main(String[] args) throws IOException {
        new WebServer().start(getPort());
    }

    public void start(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new CodeStoryHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.printf("Server is listening on port %d\n", port);
    }

    public void stop() {
        server.stop(0);
    }

    public static final int DEFAULT_PORT = 8080;
    private static int getPort() {
        String envPort = System.getenv("PORT");
        return envPort != null ? Integer.valueOf(envPort) : DEFAULT_PORT;
    }
}
