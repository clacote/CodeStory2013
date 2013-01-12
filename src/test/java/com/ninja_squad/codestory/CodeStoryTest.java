package com.ninja_squad.codestory;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.Random;

import static org.fest.assertions.Assertions.assertThat;

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
    public void deuxPlusDeux() throws Exception {
        final String answer = ask("q=2+2");
        assertThat(answer).isEqualTo("4");
    }

    @Test
    public void anyAddition() throws Exception {
        final Random random = new Random();
        final int randomA = random.nextInt(100);
        final int randomB = random.nextInt(100);
        final String answer = ask("q=" + randomA + "+" + randomB);
        assertThat(answer).isEqualTo("" + (randomA + randomB));
    }

    @Test
    public void anyMultiplication() throws Exception {
        final Random random = new Random();
        final int randomA = random.nextInt(10);
        final int randomB = random.nextInt(10);
        final String answer = ask("q=" + randomA + "*" + randomB);
        assertThat(answer).isEqualTo("" + (randomA * randomB));
    }

    @Test
    public void anySubstraction() throws Exception {
        final Random random = new Random();
        final int randomA = random.nextInt(100);
        final int randomB = random.nextInt(100);
        final String answer = ask("q=" + randomA + "-" + randomB);
        assertThat(answer).isEqualTo("" + (randomA - randomB));
    }

    @Test
    public void additionWithNegative() throws Exception {
        final String answer = ask("q=-12+10");
        assertThat(answer).isEqualTo("-2");
    }

    @Test
    public void decimalResult() throws Exception {
        final String answer = ask("q=(1+2)/2");
        assertThat(answer).isEqualTo("1,5");
    }

    @Test
    public void decimalInput() throws Exception {
        final String answer = ask("q=1,5*4");
        assertThat(answer).isEqualTo("6");
    }

    @Test
    public void bigInput() throws Exception {
        final String answer = ask("q=((1,1+2)+3,14+4+(5+6+7)+(8+9+10)*4267387833344334647677634)/2*553344300034334349999000");
        assertThat(answer).isEqualTo("31878018903828900000000000000000000000000000000000");
    }

    @Test
    public void premierEnnonceRecu() throws Exception {
        final String answer = ask("q=As+tu+bien+recu+le+premier+enonce(OUI/NON)");
        assertThat(answer).isEqualTo("OUI");
    }

    @Test
    public void scalascel1() throws Exception {
        final String answer = get("/scalaskel/change/1");
        assertThat(answer).contains("{\"foo\":1}");
    }

    @Test
    public void scalascel7() throws Exception {
        final String answer = get("/scalaskel/change/7");
        assertThat(answer)
                .contains("{\"foo\":7}")
                .contains("{\"bar\":1}");
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
        return Request.Get(getURL("/?" + query)).execute().returnContent().asString();
    }

    private String get(final String path) throws Exception {
        return Request.Get(getURL(path)).execute().returnContent().asString();
    }

    @Test
    public void badRequest() throws Exception {
        final HttpResponse response = Request.Get(getURL("/?q=UnknownQuestion")).execute().returnResponse();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
    }

    @Test
    public void root() throws Exception {
        final HttpResponse response = Request.Get(getURL("/")).execute().returnResponse();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
    }

    @Test
    public void otherMethod() throws Exception {
        final HttpResponse response = Request.Put(getURL("/")).execute().returnResponse();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_IMPLEMENTED);
    }

    private String getURL(String query) {
        return "http://localhost:" + PORT + query;
    }
}
