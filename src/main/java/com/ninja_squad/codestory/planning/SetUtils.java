package com.ninja_squad.codestory.planning;

import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * Tools about {@link Set}
 */
public class SetUtils {

    /**
     * Return all
     *
     * @param items
     * @param <T>
     * @return
     */
    public static <T> Set<List<T>> powerPermutations(Set<T> items) {
        Set<List<T>> result = Sets.newHashSet();

        Set<Set<T>> subsets = Sets.powerSet(items);
        for (Set<T> subset : subsets) {
            result.addAll(Collections2.permutations(subset));
        }

        return result;
    }
}
