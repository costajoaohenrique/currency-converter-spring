package com.currency.converter.domain;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RateCalculateTest {

    private RateCalculator rateCalculator =  new RateCalculator();

    private Map<Currency, BigDecimal> rates = new HashMap<>();

    @BeforeEach
    public void before(){
        rates.put(Currency.USD, new BigDecimal(1.17885));
        rates.put(Currency.BRL, new BigDecimal(6.192381));
        rates.put(Currency.EUR, new BigDecimal(1));
        rates.put(Currency.JPY, new BigDecimal(129.044031));
    }

    @Test
    public void shouldCalculateRateBRLtoUSD(){
        //GIVEN
        Currency currencyOrigin = Currency.BRL;
        Currency currencyDestination = Currency.USD;
        //WHEN
        BigDecimal rate = rateCalculator.calculate(currencyOrigin, currencyDestination, rates);
        //THEN
        assertThat(rate).isEqualByComparingTo(new BigDecimal("5.252899860033083"));
    }

    @Test
    public void shouldCalculateRateJPYtoUSD(){
        //GIVEN
        Currency currencyOrigin = Currency.JPY;
        Currency currencyDestination = Currency.USD;
        //WHEN
        BigDecimal rate = rateCalculator.calculate(currencyOrigin, currencyDestination, rates);
        //THEN
        assertThat(rate).isEqualByComparingTo(new BigDecimal("109.4660313016923"));
    }

    @Test
    public void shouldCalculateRateJPYtoEUR(){
        //GIVEN
        Currency currencyOrigin = Currency.JPY;
        Currency currencyDestination = Currency.EUR;
        //WHEN
        BigDecimal rate = rateCalculator.calculate(currencyOrigin, currencyDestination, rates);
        //THEN
        assertThat(rate).isEqualByComparingTo(new BigDecimal("129.044031"));
    }


}
