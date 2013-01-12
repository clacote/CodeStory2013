package com.ninja_squad.codestory.scalaskel;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class ChangeComputer {

    public Set<Map<Unite, Integer>> change(final int sum) {
        Set<Map<Unite, Integer>> results = Sets.newHashSet();
        Map<Unite, Integer> initial = Maps.newEnumMap(Unite.class);
        change(sum, initial, results);
        return results;
    }

    public void change(final int sum, Map<Unite, Integer> candidate, Set<Map<Unite, Integer>> results) {

        if (sum == 0) {
            results.add(candidate);
        } else {

            for (Unite unite : Unite.values()) {
                int remaining = sum - unite.getValue();

                if (remaining >= 0) {
                    // We could use one more of this unit
                    Map<Unite, Integer> nextCandidates = Maps.newEnumMap(candidate);
                    int nb = candidate.get(unite) != null ? candidate.get(unite) : 0;
                    nextCandidates.put(unite, nb + 1);
                    change(remaining, nextCandidates, results);
                }
            }
        }
    }

    private static ChangeComputer ourInstance = new ChangeComputer();

    public static ChangeComputer getInstance() {
        return ourInstance;
    }

    private ChangeComputer() {
    }
}
