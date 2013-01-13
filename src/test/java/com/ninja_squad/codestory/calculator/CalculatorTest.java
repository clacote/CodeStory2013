package com.ninja_squad.codestory.calculator;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Unit tests for {@link Calculator}
 */
public class CalculatorTest {

    private Calculator calculator = new Calculator();

    @Test
    public void testCalculate() throws Exception {
        assertCalculation("1+1", "2");
    }

    private void assertCalculation(final String query, final String expectedResult) {
        final String result = calculator.calculate(query);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testManageFrenchLocale() throws Exception {
        final String managed = calculator.manageFrenchLocale("1,1+2,2");
        assertThat(managed).isEqualTo("1.1+2.2");
    }

    @Test
    public void testManageFrenchLocaleNotDestructive() throws Exception {
        final String managed = calculator.manageFrenchLocale("1,1/2,2+(2^3,8)=1.1");
        assertThat(managed).isEqualTo("1.1/2.2+(2^3.8)=1.1");
    }

    @Test
    public void testManageBigDecimal() throws Exception {
        final String managed = calculator.manageBigDecimal("1.1+2.2");
        // FIXME
        // assertThat(managed).isEqualTo("1.1G+2.2G");
    }
}
