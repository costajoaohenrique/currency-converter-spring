package com.currency.converter.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTest {

    @Test
    public void shouldCreateTransactionBRLtoUSD(){
        //GIVEN
        Long idUser = 15252L;
        Currency originCurrency = Currency.BRL;
        BigDecimal originalValue = new BigDecimal(25);
        Currency destinationCurrency = Currency.USD;
        BigDecimal convertionRate = new BigDecimal("0.19140121");
        //WHEN
        Transaction transaction = new Transaction(idUser,originCurrency,originalValue,destinationCurrency,convertionRate);
        //THEN
        assertThat(transaction.getDestinationValue()).isEqualByComparingTo(new BigDecimal("4.78503025"));
    }

    @Test
    public void shouldCreateTransactionUSDtoBRL(){
        //GIVEN
        Long idUser = 15252L;
        Currency originCurrency = Currency.BRL;
        BigDecimal originalValue = new BigDecimal(25);
        Currency destinationCurrency = Currency.USD;
        BigDecimal convertionRate = new BigDecimal("5.213412");
        //WHEN
        Transaction transaction = new Transaction(idUser,originCurrency,originalValue,destinationCurrency,convertionRate);
        //THEN
        assertThat(transaction.getDestinationValue()).isEqualByComparingTo(new BigDecimal("130.3353"));
    }

}
