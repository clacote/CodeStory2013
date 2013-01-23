package com.ninja_squad.codestory.planning;

import com.google.common.collect.Lists;
import com.ninja_squad.codestory.Mesurable;
import com.ninja_squad.codestory.Mesurator;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.fest.assertions.Assertions.assertThat;

public class OptimisatorTest {

    final private Optimisator optimisator = new Optimisator();

    @Test
    public void computeOptimum() throws Exception {

        final List<Vol> vols = Lists.newArrayList(
                new Vol("TEST0", 0, 5, 2),
                new Vol("MONAD42", 0, 5, 10),
                new Vol("META18", 3, 7, 14),
                new Vol("LEGACY01", 5, 9, 8),
                new Vol("YAGNI17", 5, 9, 7),
                new Vol("TEST1", 15, 1, 5),
                new Vol("TEST2", 15, 10, 8),
                new Vol("TEST3", 16, 10, 6),
                new Vol("TEST4", 6, 10, 2)
        );
        final Planning optimum = optimisator.computeOptimum(vols);
        assertThat(optimum.getPath()).onProperty("nom").isEqualTo(Lists.newArrayList("MONAD42", "LEGACY01", "TEST1", "TEST3"));
        assertThat(optimum.getGain()).isEqualTo(29);
    }

    @Test
    public void computeOptimumIsFirstAlone() throws Exception {

        final List<Vol> vols = Lists.newArrayList(
                new Vol("TEST0", 0, 6, 10),
                new Vol("TEST1", 0, 5, 4),
                new Vol("TEST2", 5, 10, 4)
        );
        final Planning optimum = optimisator.computeOptimum(vols);
        assertThat(optimum.getPath()).onProperty("nom").isEqualTo(Lists.newArrayList("TEST0"));
        assertThat(optimum.getGain()).isEqualTo(10);
    }

    @Test
    public void computeOptimumIsLastAlone() throws Exception {

        final List<Vol> vols = Lists.newArrayList(
                new Vol("TEST1", 0, 5, 4),
                new Vol("TEST2", 3, 10, 4),
                new Vol("TEST3", 4, 6, 10)
        );
        final Planning optimum = optimisator.computeOptimum(vols);
        assertThat(optimum.getPath()).onProperty("nom").isEqualTo(Lists.newArrayList("TEST3"));
        assertThat(optimum.getGain()).isEqualTo(10);
    }

    @Test
    public void computeOptimumIsNotLastAlone() throws Exception {

        final List<Vol> vols = Lists.newArrayList(
                new Vol("TEST1", 0, 5, 5),
                new Vol("TEST2", 5, 10, 6),
                new Vol("TEST3", 6, 6, 10)
        );
        final Planning optimum = optimisator.computeOptimum(vols);
        assertThat(optimum.getPath()).onProperty("nom").isEqualTo(Lists.newArrayList("TEST1", "TEST3"));
        assertThat(optimum.getGain()).isEqualTo(15);
    }

    @Test
    public void computeReal() throws Exception {

        final List<Vol> vols = Lists.newArrayList(
                new Vol("voiceless-regime-17", 0, 4, 13),
                new Vol("brainy-ufo-15", 1, 2, 1),
                new Vol("mushy-landscape-94", 2, 6, 1),
                new Vol("calm-keystroke-35", 4, 5, 7),
                new Vol("proud-thunderstorm-91", 5, 2, 2),
                new Vol("misty-puzzle-15", 5, 4, 14),
                new Vol("wild-tugboat-98", 6, 2, 5),
                new Vol("grumpy-cane-60", 7, 6, 6),
                new Vol("dull-summertime-36", 9, 5, 4),
                new Vol("soft-beige-12", 10, 2, 25)
        );
        final Planning optimum = optimisator.computeOptimum(vols);
        assertThat(optimum.getPath()).onProperty("nom").isEqualTo(Lists.newArrayList("voiceless-regime-17", "misty-puzzle-15", "soft-beige-12"));
        assertThat(optimum.getGain()).isEqualTo(52);
    }

    @Test
    public void perf() {
        final List<Vol> vols = createRandom(1000, 50000);
        new Mesurator().mesure(new Mesurable() {
            @Override
            public void run() throws Exception {
                optimisator.computeOptimum(vols);
            }
        });

        // 23/01/2013 04H00 : Average = 250ms pour 10000 vols.
        // 23/01/2013 22H00 : Average = 150ms pour 10000 vols, 3000ms pour 50000 vols.
        // 23/01/2013 23H00 : Average = 100ms pour 50000 vols sur 24H, 3000ms pour 50000 vols.
    }

    private List<Vol> createRandom(final int length, final int nb) {
        Random random = new Random();
        final List<Vol> vols = Lists.newArrayListWithExpectedSize(nb);
        for (int i = 0; i < nb; i++) {
            int start = random.nextInt(length);
            int duree = random.nextInt(24);
            vols.add(new Vol("vol" + i, start, duree, random.nextInt(10)));
        }
        return vols;
    }
}
