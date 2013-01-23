package com.ninja_squad.codestory.planning;

import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Optimisator {

    public static final Comparator<Vol> ORDERED_VOL_CMP = new Comparator<Vol>() {
        @Override
        public int compare(Vol v1, Vol v2) {
            return Integer.valueOf(v1.getDepart()).compareTo(v2.getDepart());
        }
    };

    public Planning computeOptimum(List<Vol> vols) {

        // Vols ordonnés pas heure de début décroissante
        // Les vols sont a priori déjà ordonnées par heure de début : on évite un tri pour les perfs
        // Collections.sort(vols, ORDERED_VOL_CMP);

        // Map des meilleurs plannings par départ
        Map<Integer, Planning> bests = Maps.newHashMapWithExpectedSize(vols.size());

        // Iterate over vols (from later to sooner)
        // and compute best planning starting from each
        Planning best = null;
        for (int v = vols.size() - 1; v >= 0; --v) {
            Vol current = vols.get(v);
            // Find following vol which is time compatible
            Planning bestForVol = null;
            for (int f = v + 1; bestForVol == null && f < vols.size(); f++) {
                Vol following = vols.get(f);
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

            bests.put(current.getDepart(), best);
        }

        return best;
    }
}
