package com.ninja_squad.codestory.calculator;

import com.google.common.annotations.VisibleForTesting;
import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class Calculator {

    public String calculate(final String query) {
        String result = null;

        String preparedQuery = prepare(query);

        GroovyShell shell = new GroovyShell(new Binding());
        try {
            Object value = shell.evaluate(preparedQuery);
            if (value != null) {
                result = getNumberFormat().format(value);
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

        // Set numeric litterals as Groovy BigDecimals
        preparedQuery = manageBigDecimal(preparedQuery);

        return preparedQuery;
    }

    @VisibleForTesting
    protected String manageFrenchLocale(String query) {
        return query.replace(',', '.');
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9\\.]+");

    @VisibleForTesting
    protected String manageBigDecimal(String preparedQuery) {
        return preparedQuery;
    }

    private NumberFormat getNumberFormat() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.FRENCH);
        return new DecimalFormat("0.##", dfs);
    }
}