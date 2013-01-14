package com.ninja_squad.codestory.planning;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class OptimisatorTest {

    private Optimisator optimisator = new Optimisator();

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
}
