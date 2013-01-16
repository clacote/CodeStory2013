package com.ninja_squad.codestory.planning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import java.util.Comparator;
import java.util.Set;

public class Optimisator {

    public Planning computeOptimum(Set<Vol> vols) {
        // Order vols by startTime
        ImmutableList<Vol> orderedVols = Ordering.from(new Comparator<Vol>() {
            @Override
            public int compare(Vol v1, Vol v2) {
                return Integer.compare(v1.getDepart(), v2.getDepart());
            }
        }).immutableSortedCopy(vols);

        Planning best = new Planning();

        for (int v = 0; v < orderedVols.size(); ++v) {
            Vol vol = orderedVols.get(v);

            // on calcule le meilleur chemin pour ce vol seulement si ce vol n'est pas déjà dans le meilleur chemin
            if (!best.contains(vol)) {
                Planning possible = best(v, orderedVols);
                if (possible.compareTo(best) > 0) {
                    best = possible;
                }
            }
        }

        return best;
    }

    private Planning best(int startIndex, ImmutableList<Vol> vols) {
        Planning best = null;

        Vol start = vols.get(startIndex);
        if (startIndex < vols.size() - 1) {
            for (int v = startIndex + 1; v < vols.size(); ++v) {
                Vol next = vols.get(v);
                if (start.canBeFollowedBy(next)) {
                    Planning nextBest = best(v, vols);
                    if (best == null || nextBest.compareTo(best) > 0) {
                        best = nextBest;
                    }
                }
            }
        }

        if (best == null) {
            best = new Planning();
        }
        best.addFirst(start);
        return best;
    }
}
