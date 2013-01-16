package com.ninja_squad.codestory.planning;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ninja_squad.codestory.Mesurable;
import com.ninja_squad.codestory.Mesurator;
import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class OptimisatorTest {

    final private Optimisator optimisator = new Optimisator();

    @Test
    public void computeOptimum() throws Exception {

        final Set<Vol> vols = Sets.newHashSet(
                new Vol("MONAD42", 0, 5, 10),
                new Vol("META18", 3, 7, 14),
                new Vol("LEGACY01", 5, 9, 8),
                new Vol("YAGNI17", 5, 9, 7)
        );
        final Planning optimum = optimisator.computeOptimum(vols);
        assertThat(optimum.getPath()).onProperty("nom").isEqualTo(Lists.newArrayList("MONAD42", "LEGACY01"));
        assertThat(optimum.getGain()).isEqualTo(18);
    }

    @Test
    public void perf() {
        final Set<Vol> vols = Sets.newHashSet();
        for (int i = 0; i < 9; i++) {
            vols.add(new Vol("vol" + 1, i, 2, 10));
        }

        new Mesurator().mesure(new Mesurable() {
            @Override
            public void run() throws Exception {
                optimisator.computeOptimum(vols);
            }
        });

        // 15/01/2013 19H10 : Average = 2244ms.
    }
}
