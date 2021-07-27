package com.currency.converter.controllers;

import com.currency.converter.domain.Currency;
import com.currency.converter.dto.TransactionRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static com.currency.converter.controllers.JsonUtils.asJson;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ValidationErrorHandlerTest {

    @Autowired
    private MockMvc mockMvc;

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
        result.andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Should validate the required fields showing details in the response")
    public void shouldValidateTheRequiredFields() throws Exception {
        //GIVEN
        var originCurrency = Currency.BRL;
        var originalValue = new BigDecimal(25);
        var destinationCurrency = Currency.USD;
        var transactionRequest = new TransactionRequestDTO (
                null,
                originCurrency,
                null,
                destinationCurrency);
        //WHEN
        ResultActions result = mockMvc.perform(post("/transactions")
                .content(asJson(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON));
        //THEN
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.title").value("Invalid Fields"))
                .andExpect(jsonPath("$.detail").value("The fields passed in the request are not valid"))
                .andExpect(jsonPath("$.fields[0].name").value("idUser"))
                .andExpect(jsonPath("$.fields[0].message").value("must not be null"))
                .andExpect(jsonPath("$.fields[1].name").value("originalValue"))
                .andExpect(jsonPath("$.fields[1].message").value("must not be null"));

    }

    @Test
    @DisplayName("Should validate original value if it is greater than zero")
    public void shouldValidateOriginalValueGreaterThanZero() throws Exception {
        //GIVEN
        var originCurrency = Currency.BRL;
        var originalValue = BigDecimal.ZERO;
        var destinationCurrency = Currency.USD;
        var transactionRequest = new TransactionRequestDTO (
                222L,
                originCurrency,
                originalValue,
                destinationCurrency);
        //WHEN
        ResultActions result = mockMvc.perform(post("/transactions")
                .content(asJson(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON));
        //THEN
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.title").value("Invalid Fields"))
                .andExpect(jsonPath("$.detail").value("The fields passed in the request are not valid"))
                .andExpect(jsonPath("$.fields[0].name").value("originalValue"))
                .andExpect(jsonPath("$.fields[0].message").value("must be greater than 0.0"));
    }


    @Test
    @DisplayName("Should validate the fields currencies")
    public void shouldValidateTheFieldCurrencies() throws Exception {
        //GIVEN
        var originCurrency = Currency.BRL;
        var originalValue = BigDecimal.ZERO;
        var destinationCurrency = Currency.USD;
        var transactionRequest = new TransactionRequestDTO (
                222L,
                originCurrency,
                originalValue,
                destinationCurrency);
        //WHEN
        var jsonRequestWithInvalidsCurrencies = asJson(transactionRequest)
                .replace("BRL", "LALALALA")
                .replace("USD", "T+INVALID");

        var result = mockMvc.perform(post("/transactions")
                .content(jsonRequestWithInvalidsCurrencies)
                .contentType(MediaType.APPLICATION_JSON));
        //THEN
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.title").value("Deserialize Error"));
    }

}
