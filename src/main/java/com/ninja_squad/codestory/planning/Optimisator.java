package com.ninja_squad.codestory.planning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class Optimisator {

    public static final Comparator<Vol> INVERSED_VOL_CMP = new Comparator<Vol>() {
        @Override
        public int compare(Vol v1, Vol v2) {
            // Inversed
            return Integer.valueOf(v2.getDepart()).compareTo(v1.getDepart());
        }
    };

    public Planning computeOptimum(Set<Vol> vols) {

        // Vols ordonnés pas heure de début décroissante
        ImmutableList<Vol> orderedVols = Ordering.from(INVERSED_VOL_CMP).immutableSortedCopy(vols);

        // Map des meilleurs plannings par départ
        Map<Integer, Planning> bests = Maps.newHashMapWithExpectedSize(vols.size());

        // Iterate over vols (from later to sooner)
        // and compute best planning starting from each
        Planning best = null;
        for (int v = 0; v < orderedVols.size(); ++v) {
            Vol current = orderedVols.get(v);
            // Find following vol which is time compatible
            Planning bestForVol = null;
            for (int f = v - 1; bestForVol == null && f >= 0; f--) {
                Vol following = orderedVols.get(f);
                if (current.canBeFollowedBy(following)) {
                    bestForVol = bests.get(following.getDepart());
                }
            }
            if (bestForVol == null) {
                bestForVol = new Planning(current);
            } else {
                bestForVol = new Planning(current, bestForVol);
            }

            if (best == null || best.compareTo(bestForVol) < 0) {
                best = bestForVol;
            }

            bests.put(current.getDepart(), bestForVol);
        }

        return best;
    }
}
