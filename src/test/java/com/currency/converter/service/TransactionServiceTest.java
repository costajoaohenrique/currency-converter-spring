package com.currency.converter.service;

import com.currency.converter.domain.ConversionRateCalculator;
import com.currency.converter.domain.Currency;
import com.currency.converter.dto.TransactionRequestDTO;
import com.currency.converter.facade.ExchangeRateFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private TransactionService transactionService;

    @Mock
    private ExchangeRateFacade exchangeRateFacade;

    @Mock
    private ConversionRateCalculator conversionRateCalculator;

    private Map<Currency, BigDecimal> rates = new HashMap<>();

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.transactionService = new TransactionService(exchangeRateFacade, conversionRateCalculator);

        rates.put(Currency.USD, new BigDecimal(1.17885));
        rates.put(Currency.BRL, new BigDecimal(6.192381));
        rates.put(Currency.EUR, new BigDecimal(1));
        rates.put(Currency.JPY, new BigDecimal(129.044031));
    }



    @Test
    @DisplayName("Should Create Basic Transaction")
    public void shouldCreateBasicTransaction(){
        //GIVEN
        var idUser = 15252L;
        var originCurrency = Currency.BRL;
        var originalValue = new BigDecimal(25);
        var destinationCurrency = Currency.USD;
        var transactionRequest = new TransactionRequestDTO (
                idUser,
                originCurrency,
                originalValue,
                destinationCurrency);
        //WHEN
        when(exchangeRateFacade.getRates()).thenReturn(rates);
        when(conversionRateCalculator.calculate(originCurrency,destinationCurrency, rates)).thenReturn(new BigDecimal("1.66"));
        var response = this.transactionService.createTransaction(transactionRequest);
        //THEN
        assertNotNull(response.getIdTransaction());
        assertNotNull(response.getConvertionRate());
        assertNotNull(response.getConvertionRate());

        assertEquals(idUser,response.getIdUser());
        assertEquals(originCurrency,response.getOriginCurrency());
        assertEquals(originalValue,response.getOriginalValue());
        assertEquals(destinationCurrency,response.getDestinationCurrency());
    }

    @Test
    @DisplayName("Should create Transaction with correct conversion rate calculator")
    public void shouldCreateTransactionWithCorrectConversionRateCalculator(){
        //GIVEN
        var idUser = 15252L;
        var originCurrency = Currency.BRL;
        var originalValue = new BigDecimal(25);
        var destinationCurrency = Currency.USD;
        var transactionRequest = new TransactionRequestDTO (
                idUser,
                originCurrency,
                originalValue,
                destinationCurrency);
        //WHEN
        var correctConversionRate = new BigDecimal("1.584884");
        when(exchangeRateFacade.getRates()).thenReturn(rates);
        when(conversionRateCalculator.calculate(originCurrency,destinationCurrency, rates)).thenReturn(correctConversionRate);
        var response = this.transactionService.createTransaction(transactionRequest);
        //THEN
        assertEquals(correctConversionRate, response.getConvertionRate());
    }

}
