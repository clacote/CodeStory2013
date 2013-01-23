package com.ninja_squad.codestory.planning;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ninja_squad.codestory.Mesurable;
import com.ninja_squad.codestory.Mesurator;
import org.junit.Test;

import java.util.Random;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class OptimisatorTest {

    final private Optimisator optimisator = new Optimisator();

    @Test
    public void computeOptimum() throws Exception {

        final Set<Vol> vols = Sets.newHashSet(
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

        final Set<Vol> vols = Sets.newHashSet(
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

        final Set<Vol> vols = Sets.newHashSet(
                new Vol("TEST1", 0, 5, 4),
                new Vol("TEST2", 5, 10, 4),
                new Vol("TEST3", 0, 6, 10)
        );
        final Planning optimum = optimisator.computeOptimum(vols);
        assertThat(optimum.getPath()).onProperty("nom").isEqualTo(Lists.newArrayList("TEST3"));
        assertThat(optimum.getGain()).isEqualTo(10);
    }

    @Test
    public void perf() {
        final Set<Vol> vols = createRandom(10000);
        new Mesurator().mesure(new Mesurable() {
            @Override
            public void run() throws Exception {
                optimisator.computeOptimum(vols);
            }
        });

        // 23/01/2013 04H00 : Average = 250ms pour 10000 vols.
    }

    private Set<Vol> createRandom(final int nb) {
        Random random = new Random();
        final Set<Vol> vols = Sets.newHashSetWithExpectedSize(nb);
        for (int i = 0; i < nb; i++) {
            int start = random.nextInt(24);
            int duree = random.nextInt(24 - start);
            vols.add(new Vol("vol" + i, start, duree, random.nextInt(10)));
        }
        return vols;
    }
}
