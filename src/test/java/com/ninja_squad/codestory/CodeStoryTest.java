package com.ninja_squad.codestory;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import static org.fest.assertions.Assertions.assertThat;

import org.apache.http.entity.ContentType;
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

    @Test
    public void step3() throws Exception {
        final String answer = ask("q=Es+tu+heureux+de+participer(OUI/NON)");
        assertThat(answer).isEqualTo("OUI");
    }

    @Test
    public void step4() throws Exception {
        final String answer = ask("q=Es+tu+pret+a+recevoir+une+enonce+au+format+markdown+par+http+post(OUI/NON)");
        assertThat(answer).isEqualTo("OUI");
    }

    @Test
    public void toujoursOui() throws Exception {
        final String answer = ask("q=Est+ce+que+tu+reponds+toujours+oui(OUI/NON)");
        assertThat(answer).isEqualTo("NON");
    }

    @Test
    public void unPlusUn() throws Exception {
        final String answer = ask("q=1+1");
        assertThat(answer).isEqualTo("2");
    }

    @Test
    public void post() throws Exception {
        final HttpResponse response = Request.Post(getURL("/?q"))
            .bodyString("Une question Markdown", ContentType.TEXT_PLAIN)
            .execute()
            .returnResponse();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_CREATED);
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
    public void otherMethod() throws Exception {
        final HttpResponse response = Request.Put(getURL("/")).execute().returnResponse();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_IMPLEMENTED);
    }

    private String getURL(String query) {
        return "http://localhost:"+PORT+query;
    }
}
