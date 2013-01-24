package com.ninja_squad.codestory.planning;

import org.apache.commons.collections.map.LRUMap;

import java.util.*;

public class Optimisator {

    private static final Comparator<Vol> ORDERED_VOL_CMP = new Comparator<Vol>() {
        @Override
        public int compare(Vol v1, Vol v2) {
            return Integer.valueOf(v1.getDepart()).compareTo(v2.getDepart());
        }
    };

    public Planning computeOptimum(List<Vol> vols) {

        // Sort vols by start date
        Collections.sort(vols, ORDERED_VOL_CMP);

        // Map des meilleurs plannings par heure de départ
        Map<Integer, Planning> bests = new LRUMap(getMaxDureeVol(vols));

        // Liste des heures de départ effectives
        LinkedList<Integer> usedStartHours = new LinkedList<Integer>();

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

            // Manage used start hours
            if (usedStartHours.isEmpty() || !(usedStartHours.getFirst().intValue() == current.getDepart())) {
                usedStartHours.addFirst(current.getDepart());
            }
        }

        return best;
    }

    private int getMaxDureeVol(List<Vol> vols) {
        // Actuellement aucun vol de plus de 24H dans les données CodeStory.
        // Si ça arrivait, il suffirait de calculer ici le max dans vols des Vol.duree.
        return 24;
    }

    private int findNextDeparture(int sooner, List<Integer> startHours) {
        for (Integer startHour : startHours) {
            if (startHour.intValue() >= sooner) {
                return startHour.intValue();
            }
        }
        return -1;
    }
}
