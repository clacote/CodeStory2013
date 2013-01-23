package com.ninja_squad.codestory;

import com.ninja_squad.codestory.planning.Vol;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class JSONTest {

    @Test
    public void toVols() throws Exception {
        final String json = "[\n" +
                "  {\"VOL\": \"AF514\", \"DEPART\":0, \"DUREE\":5, \"PRIX\": 10}\n" +
                "\n" +
                "]";
        final List<Vol> vols = JSON.toVols(json);
        assertThat(vols)
                .hasSize(1)
                .containsOnly(new Vol("AF514", 0, 5, 10));
    }
}
