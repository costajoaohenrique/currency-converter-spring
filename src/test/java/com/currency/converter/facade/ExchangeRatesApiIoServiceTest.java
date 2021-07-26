package com.currency.converter.facade;

import com.currency.converter.domain.Currency;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ExchangeRatesApiIoServiceTest {

    @Autowired
    private ExchangeRateFacade facade;

    @Test
    @DisplayName("Should Get Rates In Api")
    public void shouldGetRatesInApi ()
    {
        //GIVEN
        //WHEN
        var rates = facade.getRates();
        //THEN
        assertNotNull(rates);
        assertTrue(rates.size() != 0);
    }


    @Test
    @DisplayName("Should Get Rates In Api With All Currency")
    public void shouldGetRatesInApiWithAllCurrency ()
    {
        //GIVEN
        Currency[] values = Currency.values();
        //WHEN
        var rates = facade.getRates();
        //THEN
        for (int i = 0; i < values.length; i++) {
            assertTrue(rates.containsKey(values[i]));
        }
    }
}
