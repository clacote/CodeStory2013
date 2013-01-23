package com.ninja_squad.codestory.planning;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import java.util.*;

public class Optimisator {

    private static final Comparator<Vol> ORDERED_VOL_CMP = new Comparator<Vol>() {
        @Override
        public int compare(Vol v1, Vol v2) {
            return Integer.valueOf(v1.getDepart()).compareTo(v2.getDepart());
        }
    };

    private static final Function<Vol, Integer> VOL_START_HOUR = new Function<Vol, Integer>() {
        @Override
        public Integer apply(Vol input) {
            return input.getDepart();
        }
    };

    public Planning computeOptimum(List<Vol> vols) {

        Collections.sort(vols, ORDERED_VOL_CMP);
        LinkedList<Integer> usedStartHours = new LinkedList<Integer>();
        int previous = -1;
        for (Vol v : vols) {
            if (previous != v.getDepart()) {
                usedStartHours.addLast(v.getDepart());
            }
            previous = v.getDepart();
        }

        // Map des meilleurs plannings par heure de départ
        Map<Integer, Planning> bests = Maps.newHashMapWithExpectedSize(usedStartHours.size());

        // Iterate over vols (from later to sooner)
        // and compute best planning starting from each
        Planning best = null;
        for (int v = vols.size() - 1; v >= 0; --v) {
            Vol current = vols.get(v);
            // Find best next following departure
            int nextDeparture = findNextDeparture(current.getArrivee(), usedStartHours);
            Planning bestForVol = bests.get(nextDeparture);

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

    private int findNextDeparture(int sooner, List<Integer> startHours) {
        int result = Integer.MAX_VALUE;
        for (int i = startHours.size() - 1; i > 0 && sooner < result; i--) {
            if (startHours.get(i) >= sooner) {
                result = startHours.get(i);
            }
        }
        return result;
    }
}
