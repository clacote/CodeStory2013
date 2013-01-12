package com.ninja_squad.codestory.scalaskel;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class ChangeComputer {

    public Set<Map<Unite, Integer>> change(final int sum) {
        Map<Unite, Integer> current = Maps.newEnumMap(Unite.class);
        return change(sum, current);
    }


    public Set<Map<Unite, Integer>> change(final int sum, Map<Unite, Integer> candidate) {
        Set<Map<Unite, Integer>> result = Sets.newHashSet();

        for (Unite unite : Unite.values()) {
            int remaining = sum - unite.getValue();
            if (remaining >= 0) {
                // We could use one more of this unit
                Map<Unite, Integer> nextCandidates = Maps.newHashMap(candidate);
                int nb = candidate.get(unite) != null ? candidate.get(unite) : 0;
                nextCandidates.put(unite, nb + 1);

                if (remaining == 0) {
                    // Valid change
                    result.add(nextCandidates);
                } else {
                    // Keep looking
                    result.addAll(change(remaining, nextCandidates));
                }
            }
        }

        return result;
    }

    private static ChangeComputer ourInstance = new ChangeComputer();

    public static ChangeComputer getInstance() {
        return ourInstance;
    }

    private ChangeComputer() {
    }
}
