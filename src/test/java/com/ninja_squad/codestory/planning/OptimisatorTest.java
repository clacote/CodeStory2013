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
    public void perf() {
        final Set<Vol> vols = createRandom(50);
        new Mesurator().mesure(new Mesurable() {
            @Override
            public void run() throws Exception {
                optimisator.computeOptimum(vols);
            }
        });

        // 17/01/2013 23H10 : Average = 2537ms.
        // 17/01/2013 23H15 : Average = 600ms.
    }

    private Set<Vol> createRandom(final int nb) {
        Random random = new Random();
        final Set<Vol> vols = Sets.newHashSetWithExpectedSize(nb);
        for (int i = 0; i < nb; i++) {
            vols.add(new Vol("vol" + i, random.nextInt(24), random.nextInt(5), random.nextInt(10)));
        }
        return vols;
    }
}
