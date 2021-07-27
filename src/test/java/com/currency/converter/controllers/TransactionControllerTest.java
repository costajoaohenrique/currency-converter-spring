package com.currency.converter.controllers;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.Transaction;
import com.currency.converter.dto.TransactionRequestDTO;
import com.currency.converter.repositories.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static com.currency.converter.domain.Currency.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should save transaction")
    public void shouldSaveTransaction() throws Exception {
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
        ResultActions result = mockMvc.perform(post("/transactions")
                .content(asJson(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON));
        //THEN
        result.andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Should not save transaction with null fields")
    public void shouldNotSaveTransactionWithNullFields() throws Exception {
        //GIVEN
        var originCurrency = Currency.BRL;
        var originalValue = new BigDecimal(25);
        var destinationCurrency = Currency.USD;
        var transactionRequest = new TransactionRequestDTO (
                null,
                originCurrency,
                originalValue,
                destinationCurrency);
        //WHEN
        ResultActions result = mockMvc.perform(post("/transactions")
                .content(asJson(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON));
        //THEN
        result.andExpect(status().isCreated());

    }

    private static String asJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
