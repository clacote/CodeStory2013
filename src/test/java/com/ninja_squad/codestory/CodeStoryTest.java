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
    public void bonneNuit() throws Exception {
        final String answer = ask("q=As+tu+passe+une+bonne+nuit+malgre+les+bugs+de+l+etape+precedente(PAS_TOP/BOF/QUELS_BUGS)");
        assertThat(answer).isEqualTo("PAS_TOP");
    }

    @Test
    public void secondEnonce() throws Exception {
        final String answer = ask("q=As+tu+bien+recu+le+second+enonce(OUI/NON)");
        assertThat(answer).isEqualTo("OUI");
    }

    @Test
    public void ndeloof() throws Exception {
        final String answer = ask("q=As+tu+copie+le+code+de+ndeloof(OUI/NON/JE_SUIS_NICOLAS)");
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
        assertThat(answer).isEqualTo("31878018903828899277492024491376690701584023926880");
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
    public void optimize() throws Exception {
        final String input = "[ " +
                "{ \"VOL\": \"bright-telescope-91\", \"DEPART\": 2, \"DUREE\": 7, \"PRIX\": 21 }, " +
                "{ \"VOL\": \"steep-farmhand-81\", \"DEPART\": 0, \"DUREE\": 1, \"PRIX\": 12 }, " +
                "{ \"VOL\": \"shrill-peephole-67\", \"DEPART\": 4, \"DUREE\": 3, \"PRIX\": 3 }, " +
                "{ \"VOL\": \"crazy-watchtower-73\", \"DEPART\": 1, \"DUREE\": 1, \"PRIX\": 6 }, " +
                "{ \"VOL\": \"curious-mainframe-35\", \"DEPART\": 4, \"DUREE\": 17, \"PRIX\": 4 }, " +
                "{ \"VOL\": \"tender-lava-90\", \"DEPART\": 9, \"DUREE\": 2, \"PRIX\": 6 }, " +
                "{ \"VOL\": \"hungry-backward-44\", \"DEPART\": 9, \"DUREE\": 1, \"PRIX\": 7 }, " +
                "{ \"VOL\": \"super-genie-14\", \"DEPART\": 6, \"DUREE\": 5, \"PRIX\": 6 }, " +
                "{ \"VOL\": \"vast-yak-32\", \"DEPART\": 8, \"DUREE\": 3, \"PRIX\": 8 }, " +
                "{ \"VOL\": \"high-pitched-dunce-44\", \"DEPART\": 9, \"DUREE\": 3, \"PRIX\": 1 } ]";
        final String response = Request.Post(getURL("/jajascript/optimize"))
                .bodyString(input, ContentType.TEXT_PLAIN)
                .execute()
                .returnContent()
                .asString();
        assertThat(response)
                .isNotEmpty()
                .contains("\"path\":[\"steep-farmhand-81\",\"crazy-watchtower-73\",\"bright-telescope-91\",\"hungry-backward-44\"]")
                .contains("\"gain\":46");
    }

    @Test
    public void perfOptimize() {
        final String json = getCodeStoryOfficialJson();
        new Mesurator().mesure(new Mesurable() {
            @Override
            public void run() throws Exception {
                Request.Post(getURL("/jajascript/optimize"))
                        .bodyString(json, ContentType.TEXT_PLAIN)
                        .execute()
                        .returnContent()
                        .asString();
            }
        });
        // 18/01/2013 12H44 : Average = 125ms.
    }

    public static String getPlanningJSonString() {
        int i = 0;
        return "[\n" +
                "  {\"VOL\": \"AF51" + (i++) + "\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10},\n" +
                "  {\"VOL\": \"AF51" + (i++) + "\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10},\n" +
                "  {\"VOL\": \"AF51" + (i++) + "\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10},\n" +
                "  {\"VOL\": \"AF51" + (i++) + "\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10},\n" +
                "  {\"VOL\": \"AF51" + (i++) + "\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10},\n" +
                "  {\"VOL\": \"AF51" + (i++) + "\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10},\n" +
                "  {\"VOL\": \"AF51" + (i++) + "\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10},\n" +
                "  {\"VOL\": \"AF51" + (i++) + "\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10},\n" +
                "  {\"VOL\": \"AF51" + (i++) + "\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10}\n" +
                "\n" +
                "]";
    }

    private static String getCodeStoryOfficialJson() {
        return "[ { \"VOL\": \"fancy-mathematician-60\", \"DEPART\": 0, \"DUREE\": 4, \"PRIX\": 7 }, { \"VOL\": \"elegant-ration-33\", \"DEPART\": 1, \"DUREE\": 2, \"PRIX\": 7 }, { \"VOL\": \"evil-*-45\", \"DEPART\": 2, \"DUREE\": 6, \"PRIX\": 1 }, { \"VOL\": \"angry-snorkel-82\", \"DEPART\": 4, \"DUREE\": 5, \"PRIX\": 21 }, { \"VOL\": \"jittery-hardwood-1\", \"DEPART\": 5, \"DUREE\": 2, \"PRIX\": 22 }, { \"VOL\": \"wide-eyed-shyness-15\", \"DEPART\": 5, \"DUREE\": 4, \"PRIX\": 11 }, { \"VOL\": \"friendly-slipper-14\", \"DEPART\": 6, \"DUREE\": 2, \"PRIX\": 3 }, { \"VOL\": \"nice-marmalade-14\", \"DEPART\": 7, \"DUREE\": 6, \"PRIX\": 7 }, { \"VOL\": \"flipped-out-tearful-25\", \"DEPART\": 9, \"DUREE\": 5, \"PRIX\": 11 }, { \"VOL\": \"shiny-harpoon-91\", \"DEPART\": 10, \"DUREE\": 2, \"PRIX\": 5 }, { \"VOL\": \"jealous-schoolmarm-38\", \"DEPART\": 10, \"DUREE\": 4, \"PRIX\": 9 }, { \"VOL\": \"victorious-sunshine-49\", \"DEPART\": 11, \"DUREE\": 2, \"PRIX\": 5 }, { \"VOL\": \"delightful-watermark-5\", \"DEPART\": 12, \"DUREE\": 6, \"PRIX\": 3 }, { \"VOL\": \"poised-sty-62\", \"DEPART\": 14, \"DUREE\": 5, \"PRIX\": 8 }, { \"VOL\": \"flat-mat-71\", \"DEPART\": 15, \"DUREE\": 2, \"PRIX\": 16 }, { \"VOL\": \"petite-vest-36\", \"DEPART\": 15, \"DUREE\": 4, \"PRIX\": 15 }, { \"VOL\": \"awful-mandrill-35\", \"DEPART\": 16, \"DUREE\": 2, \"PRIX\": 3 }, { \"VOL\": \"exuberant-tracker-81\", \"DEPART\": 17, \"DUREE\": 6, \"PRIX\": 3 }, { \"VOL\": \"wild-servant-10\", \"DEPART\": 19, \"DUREE\": 5, \"PRIX\": 23 }, { \"VOL\": \"vivacious-boat-44\", \"DEPART\": 20, \"DUREE\": 2, \"PRIX\": 19 }, { \"VOL\": \"slow-value-31\", \"DEPART\": 20, \"DUREE\": 4, \"PRIX\": 7 }, { \"VOL\": \"square-hairdresser-77\", \"DEPART\": 21, \"DUREE\": 2, \"PRIX\": 9 }, { \"VOL\": \"shy-cobbler-85\", \"DEPART\": 22, \"DUREE\": 6, \"PRIX\": 4 }, { \"VOL\": \"mysterious-zebra-7\", \"DEPART\": 24, \"DUREE\": 5, \"PRIX\": 23 }, { \"VOL\": \"combative-weirdness-3\", \"DEPART\": 25, \"DUREE\": 2, \"PRIX\": 9 }, { \"VOL\": \"proud-collie-43\", \"DEPART\": 25, \"DUREE\": 4, \"PRIX\": 10 }, { \"VOL\": \"steep-rival-75\", \"DEPART\": 26, \"DUREE\": 2, \"PRIX\": 7 }, { \"VOL\": \"grumpy-rump-85\", \"DEPART\": 27, \"DUREE\": 6, \"PRIX\": 3 }, { \"VOL\": \"long-testosterone-56\", \"DEPART\": 29, \"DUREE\": 5, \"PRIX\": 15 }, { \"VOL\": \"grieving-babyhood-92\", \"DEPART\": 30, \"DUREE\": 2, \"PRIX\": 25 }, { \"VOL\": \"shallow-monster-70\", \"DEPART\": 30, \"DUREE\": 4, \"PRIX\": 8 }, { \"VOL\": \"nutty-hijacker-98\", \"DEPART\": 31, \"DUREE\": 2, \"PRIX\": 1 }, { \"VOL\": \"shrill-surfer-57\", \"DEPART\": 32, \"DUREE\": 6, \"PRIX\": 3 }, { \"VOL\": \"square-trance-15\", \"DEPART\": 34, \"DUREE\": 5, \"PRIX\": 10 }, { \"VOL\": \"rapid-remote-55\", \"DEPART\": 35, \"DUREE\": 2, \"PRIX\": 3 } ]\n";
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
