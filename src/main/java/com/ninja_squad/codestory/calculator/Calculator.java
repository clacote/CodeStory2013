package com.ninja_squad.codestory.calculator;

import com.google.common.annotations.VisibleForTesting;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Calculator {

    public String calculate(final String query) {
        String result = null;

        String preparedQuery = prepare(query);

        GroovyShell shell = new GroovyShell();
        try {
            BigDecimal bd = (BigDecimal) shell.evaluate(preparedQuery);
            if (bd != null) {
                result = format(bd);
            }
        } catch (GroovyRuntimeException e) {
            // Invalid expression
        }
        return result;
    }


    private String prepare(String query) {
        String preparedQuery = query;

        // query might be in French LOCALE
        preparedQuery = manageFrenchLocale(preparedQuery);

        // Force Groovy to return a BigDecimal instead of (fucking rounded) Double
        preparedQuery = manageBigDecimal(preparedQuery);

        return preparedQuery;
    }

    @VisibleForTesting
    protected String manageFrenchLocale(String query) {
        return query.replace(',', '.');
    }

    @VisibleForTesting
    protected String manageBigDecimal(String query) {
        return new StringBuilder()
                .append(query)
                .append(" as BigDecimal")
                .toString();
    }

    @VisibleForTesting
    protected String format(BigDecimal bd) {
        return getNumberFormat().format(bd);
    }

    private NumberFormat getNumberFormat() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.FRENCH);
        return new DecimalFormat("0.##", dfs);
    }

}