package com.ninja_squad.codestory;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import static org.fest.assertions.Assertions.*;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.AfterClass;
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
        final String answer = ask("q=Quelle+est+ton+adresse+email");
        assertThat(answer).isEqualTo("cyril@ninja-squad.com");
    }

    @Test
    public void step2() throws Exception {
        final String answer = ask("q=Es+tu+abonne+a+la+mailing+list(OUI/NON)");
        assertThat(answer).isEqualTo("OUI");
    }

    private String ask(final String query) throws Exception {
        return Request.Get(getURL("/?"+query)).execute().returnContent().asString();
    }

    @Test
    public void badRequest() throws Exception {
        final HttpResponse response = Request.Get(getURL("/?q=UnknownQuestion")).execute().returnResponse();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
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
