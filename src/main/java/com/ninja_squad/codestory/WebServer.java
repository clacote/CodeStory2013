package com.ninja_squad.codestory;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebServer {


    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        InetSocketAddress addr = new InetSocketAddress(PORT);
        HttpServer server = HttpServer.create(addr, 0);

        server.createContext("/", new CodeStoryHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.printf("Server is listening on port %d\n", PORT);
    }}
