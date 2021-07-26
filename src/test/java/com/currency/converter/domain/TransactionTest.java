package com.currency.converter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTest {

    @Test
    @DisplayName("Should Create Transaction BRL to USD")
    public void shouldCreateTransactionBRLtoUSD(){
        //GIVEN
        var idUser = 15252L;
        var originCurrency = Currency.BRL;
        var originalValue = new BigDecimal(25);
        var destinationCurrency = Currency.USD;
        var conversionRate = new BigDecimal("0.19140121");
        //WHEN
        Transaction transaction = new Transaction(idUser,originCurrency,originalValue,destinationCurrency,conversionRate);
        //THEN
        assertThat(transaction.getDestinationValue()).isEqualByComparingTo(new BigDecimal("4.78503025"));
    }

    @Test
    @DisplayName("Should Create Transaction USD to BRL")
    public void shouldCreateTransactionUSDtoBRL(){
        //GIVEN
        var idUser = 15252L;
        var originCurrency = Currency.BRL;
        var originalValue = new BigDecimal(25);
        var destinationCurrency = Currency.USD;
        var conversionRate = new BigDecimal("5.213412");
        //WHEN
        Transaction transaction = new Transaction(idUser,originCurrency,originalValue,destinationCurrency,conversionRate);
        //THEN
        assertThat(transaction.getDestinationValue()).isEqualByComparingTo(new BigDecimal("130.3353"));
    }

}
