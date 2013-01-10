package com.ninja_squad.codestory;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import static org.fest.assertions.Assertions.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.HttpURLConnection;

public class CodeStoryTest {

    public static final int PORT = 8080;
    private static WebServer server;

    @BeforeClass
    public static void setUp() throws Exception {
        server = new WebServer();
        server.start(PORT);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void step1() throws Exception {
        final String result = Request.Get(getURL("/?q=Quelle+est+ton+adresse+email")).execute().returnContent().asString();
        assertThat(result).contains("cyril+codestory@ninja-squad.com");
    }

    @Test
    public void badMethod() throws Exception {
        final HttpResponse response = Request.Post(getURL("/")).execute().returnResponse();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_METHOD);
    }

    private String getURL(String query) {
        return "http://localhost:"+PORT+query;
    }
}
