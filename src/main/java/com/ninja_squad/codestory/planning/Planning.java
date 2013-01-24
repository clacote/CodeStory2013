package com.ninja_squad.codestory.planning;

import java.util.LinkedList;
import java.util.List;

/**
 * Immutable planning, i.e list of possible {@link Vol}
 */
public class Planning {

    private final LinkedList<Vol> path = new LinkedList<Vol>();

    private int gain = 0;

    public void add(Vol v) {
        this.path.addLast(v);
        this.gain += v.getPrix();
    }

    public int getGain() {
        return gain;
    }

    public List<Vol> getPath() {
        return path;
    }
}
