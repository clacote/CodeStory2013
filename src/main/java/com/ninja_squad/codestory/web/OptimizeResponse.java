package com.ninja_squad.codestory.web;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ninja_squad.codestory.planning.Planning;
import com.ninja_squad.codestory.planning.Vol;

import java.util.List;

public class OptimizeResponse {

    private final List<String> path;
    private final int gain;

    private static final Function<Vol, String> VOL_TO_NOM = new Function<Vol, String>() {
        @Override
        public String apply(Vol input) {
            return input.getNom();
        }
    };

    public OptimizeResponse(Planning planning) {
        this.path = Lists.transform(planning.getPath(), VOL_TO_NOM);
        this.gain = planning.getGain();
    }

    public List<String> getPath() {
        return path;
    }

    public int getGain() {
        return gain;
    }
}
