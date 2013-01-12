package com.ninja_squad.codestory.scalaskel;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

public class ChangeComputer {

    private static final Collection<List<Unite>> PERMUTATIONS = Collections2.permutations(EnumSet.allOf(Unite.class));

    public Set<Map<Unite, Integer>> change(final int sum) {
        Set<Map<Unite, Integer>> result = Sets.newHashSet();

        Map<Unite, Integer> possibleChange = Maps.newEnumMap(Unite.class);
        for (List<Unite> unites : PERMUTATIONS) {
            int remaining = sum;

            for (Unite unite : unites) {
                if (remaining >= unite.getValue()) {
                    int nb = remaining / unite.getValue();
                    if (nb > 0) {
                        possibleChange.put(unite, nb);
                        remaining %= unite.getValue();
                    }
                }
            }

            // Is it a valid change?
            if (remaining == 0) {
                // Valid change
                result.add(ImmutableMap.copyOf(possibleChange));
            }

            // Reset possibleChange for new computation
            possibleChange.clear();

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
