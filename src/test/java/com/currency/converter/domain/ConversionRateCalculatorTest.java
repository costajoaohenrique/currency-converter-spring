package com.currency.converter.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ConversionRateCalculatorTest {

    private ConversionRateCalculator conversionRateCalculator =  new ConversionRateCalculator();

    private Map<Currency, BigDecimal> rates = new HashMap<>();

    @BeforeEach
    public void beforeEach(){
        rates.put(Currency.USD, new BigDecimal(1.17885));
        rates.put(Currency.BRL, new BigDecimal(6.192381));
        rates.put(Currency.EUR, new BigDecimal(1));
        rates.put(Currency.JPY, new BigDecimal(129.044031));
    }

    @Test
    @DisplayName("Should Calculate Rate BRL to USD")
    public void shouldCalculateRateBRLtoUSD(){
        //GIVEN
        var currencyOrigin = Currency.BRL;
        var currencyDestination = Currency.USD;
        //WHEN
        var rate = conversionRateCalculator.calculate(currencyOrigin, currencyDestination, rates);
        //THEN
        assertThat(rate).isEqualByComparingTo(new BigDecimal("5.252899860033083"));
    }

    @Test
    @DisplayName("Should Calculate Rate JPY to USD")
    public void shouldCalculateRateJPYtoUSD(){
        //GIVEN
        var currencyOrigin = Currency.JPY;
        var currencyDestination = Currency.USD;
        //WHEN
        var rate = conversionRateCalculator.calculate(currencyOrigin, currencyDestination, rates);
        //THEN
        assertThat(rate).isEqualByComparingTo(new BigDecimal("109.4660313016923"));
    }

    @Test
    @DisplayName("Should Calculate Rate JPY to EUR")
    public void shouldCalculateRateJPYtoEUR(){
        //GIVEN
        var currencyOrigin = Currency.JPY;
        var currencyDestination = Currency.EUR;
        //WHEN
        var rate = conversionRateCalculator.calculate(currencyOrigin, currencyDestination, rates);
        //THEN
        assertThat(rate).isEqualByComparingTo(new BigDecimal("129.044031"));
    }


}
