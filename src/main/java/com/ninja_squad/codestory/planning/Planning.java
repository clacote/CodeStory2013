package com.ninja_squad.codestory.planning;

import java.util.LinkedList;
import java.util.List;

public class Planning implements Comparable<Planning> {

    private final LinkedList<Vol> path;

    private int gain = 0;

    public Planning() {
        path = new LinkedList<Vol>();
    }

    public boolean contains(Vol vol) {
        return this.path.contains(vol);
    }

    public void addFirst(Vol vol) {
        this.path.addFirst(vol);
        this.gain += vol.getPrix();
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
