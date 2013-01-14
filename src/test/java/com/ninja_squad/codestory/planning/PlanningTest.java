package com.ninja_squad.codestory.planning;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;

import static org.fest.assertions.Assertions.assertThat;

public class PlanningTest {

    @Test
    public void isPossibleTrue() throws Exception {
        final Planning p = new Planning(Lists.newArrayList(
                new Vol("vol1", 2, 1, 0),
                new Vol("vol2", 3, 5, 0),
                new Vol("vol3", 10, 1, 0)
        ));
        assertThat(p.isPossible()).isTrue();
    }

    @Test
    public void isPossibleFalse() throws Exception {
        final Planning p = new Planning(Lists.newArrayList(
                new Vol("vol1", 2, 1, 0),
                new Vol("vol2", 3, 5, 0),
                new Vol("vol3", 7, 1, 0)
        ));
        assertThat(p.isPossible()).isFalse();
    }

    @Test
    public void isPossibleEmpty() throws Exception {
        final Planning p = new Planning(Collections.<Vol>emptyList());
        assertThat(p.isPossible()).isTrue();
    }

    @Test
    public void getGain() throws Exception {
        final Planning p = new Planning(Lists.newArrayList(
                new Vol("vol1", 0, 0, 1),
                new Vol("vol2", 0, 0, 2),
                new Vol("vol3", 0, 0, 3)
        ));
        assertThat(p.getGain()).isEqualTo(6);
    }
}
