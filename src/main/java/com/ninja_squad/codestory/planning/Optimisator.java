package com.ninja_squad.codestory.planning;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class Optimisator {

    public Planning computeOptimum(Set<Vol> vols) {
        Planning result = null;

        if (vols != null) {
            final Set<Planning> allPlannings = computeAllPossibilities(vols);
            final Set<Planning> possiblePlannings = Sets.filter(allPlannings, Planning.POSSIBLE);
            result = Ordering.natural().max(possiblePlannings.iterator());
        }

        return result;
    }

    private Set<Planning> computeAllPossibilities(Set<Vol> vols) {
        Set<List<Vol>> possibilities = SetUtils.powerPermutations(vols);
        return FluentIterable.from(possibilities)
                .transform(new Function<List<Vol>, Planning>() {
                    @Override
                    public Planning apply(List<Vol> input) {
                        return new Planning(input);
                    }
                })
                .toImmutableSet();
    }
}
