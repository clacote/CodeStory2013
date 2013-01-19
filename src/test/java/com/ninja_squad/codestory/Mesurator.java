package com.ninja_squad.codestory;

public class Mesurator {

    private int nb = 5;

    public Mesurator() {
    }

    public Mesurator(int nb) {
        this.nb = nb;
    }

    public void mesure(Mesurable action) {
        long total = 0;
        for (int i = 0; i < nb; ++i) {
            long start = System.currentTimeMillis();
            try {
                action.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();

            total += (end - start);
            System.out.println("Exec n°" + (i + 1) + " : " + (end - start) + "ms.");
        }
        System.out.println("Average = " + (total / nb) + "ms.");
    }
}
