package com.ninja_squad.codestory.planning;

import java.util.LinkedList;
import java.util.List;

/**
 * Immutable planning, i.e list of possible {@link Vol}
 */
public class Planning implements Comparable<Planning> {

    private final LinkedList<Vol> path = new LinkedList<Vol>();

    private int gain = 0;

    public Planning(Vol v) {
        addFirst(v);
    }

    public Planning(Vol first, Planning suivant) {
        this.path.addAll(suivant.path);
        this.gain = suivant.gain;
        addFirst(first);
    }

    private void addFirst(Vol v) {
        this.path.addFirst(v);
        this.gain += v.getPrix();
    }

    public boolean contains(Vol vol) {
        return this.path.contains(vol);
    }

    public int getGain() {
        return gain;
    }

    public List<Vol> getPath() {
        return path;
    }

    @Override
    public int compareTo(Planning o) {
        return Integer.valueOf(getGain()).compareTo(o.getGain());
    }
}
