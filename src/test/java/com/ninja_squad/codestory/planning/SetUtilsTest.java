package com.ninja_squad.codestory.planning;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Unit tests for {@link SetUtils}
 */
public class SetUtilsTest {

    @Test
    public void testComputeAllPossibilities() throws Exception {
        final Set<Integer> items = Sets.newHashSet(1, 2, 3);
        final Collection<List<Integer>> possibilities = SetUtils.powerPermutations(items);

        Assertions.assertThat(possibilities).containsOnly(
                Lists.newArrayList(),
                Lists.newArrayList(1),
                Lists.newArrayList(2),
                Lists.newArrayList(3),
                Lists.newArrayList(1, 2),
                Lists.newArrayList(1, 3),
                Lists.newArrayList(2, 1),
                Lists.newArrayList(2, 3),
                Lists.newArrayList(3, 1),
                Lists.newArrayList(3, 2),
                Lists.newArrayList(1, 2, 3),
                Lists.newArrayList(1, 3, 2),
                Lists.newArrayList(2, 1, 3),
                Lists.newArrayList(2, 3, 1),
                Lists.newArrayList(3, 1, 2),
                Lists.newArrayList(3, 2, 1)
        );
    }
}