package com.ninja_squad.codestory.calculator;

import org.junit.Test;

import java.math.BigDecimal;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Unit tests for {@link Calculator}
 */
public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    public void testCalculate() throws Exception {
        assertCalculation("1+1", "2");
    }

    @Test
    public void testCalculate_big() {
        assertCalculation(
                "((1,1+2)+3,14+4+(5+6+7)+(8+9+10)*4267387833344334647677634)/2*553344300034334349999000",
                "31878018903828899277492024491376690701584023926880");
    }

    @Test
    public void testCalculate_big2() {
        assertCalculation(
                "1,0000000000000000000000000000000000000000000000001*1,0000000000000000000000000000000000000000000000001",
                "1,00000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000001");
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
    public void testFormat() {
        assertThat(calculator.format(new BigDecimal("00001.100"))).isEqualTo("1,1");
        assertThat(calculator.format(new BigDecimal("001234.000"))).isEqualTo("1234");
    }
}
