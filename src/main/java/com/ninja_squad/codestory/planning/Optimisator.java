package com.ninja_squad.codestory.planning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class Optimisator {

    public static final Comparator<Vol> ORDERED_VOL_CMP = new Comparator<Vol>() {
        @Override
        public int compare(Vol v1, Vol v2) {
            return Integer.valueOf(v1.getDepart()).compareTo(v2.getDepart());
        }
    };

    public Planning computeOptimum(Set<Vol> vols) {

        // Order vols by startTime
        ImmutableList<Vol> orderedVols = Ordering.from(ORDERED_VOL_CMP).immutableSortedCopy(vols);

        Planning best = null;
        Map<Integer, Planning> bestCache = Maps.newHashMapWithExpectedSize(vols.size());

        for (int v = 0; v < orderedVols.size(); ++v) {
            Vol vol = orderedVols.get(v);

            // on calcule le meilleur chemin pour ce vol seulement si ce vol n'est pas déjà dans le meilleur chemin
            if (best == null || !best.contains(vol)) {
                Planning possible = best(orderedVols, v, bestCache);
                if (best == null || possible.compareTo(best) > 0) {
                    best = possible;
                }
            }
        }

        return best;
    }

    private Planning best(ImmutableList<Vol> vols, int startIndex, Map<Integer, Planning> bestCache) {
        Planning best = bestCache.get(Integer.valueOf(startIndex));
        if (best != null) return best;

        Vol start = vols.get(startIndex);
        if (startIndex < vols.size() - 1) {
            for (int v = startIndex + 1; v < vols.size(); ++v) {
                Vol next = vols.get(v);
                if (start.canBeFollowedBy(next) && (best == null || !best.contains(next))) {
                    Planning nextBest = best(vols, v, bestCache);
                    if (best == null || nextBest.compareTo(best) > 0) {
                        best = nextBest;
                    }
                }
            }
        }

        if (best == null) {
            best = new Planning(start);
        } else {
            best = new Planning(start, best);
        }

        bestCache.put(Integer.valueOf(startIndex), best);

        return best;
    }
}
