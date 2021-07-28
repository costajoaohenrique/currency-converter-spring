package com.currency.converter.controllers;

import com.currency.converter.CurrencyConverterSpringApplication;
import com.currency.converter.domain.Currency;
import com.currency.converter.domain.Transaction;
import com.currency.converter.dto.TransactionRequestDTO;
import com.currency.converter.repositories.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.currency.converter.controllers.JsonUtils.asJson;
import static com.currency.converter.domain.Currency.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        var transactionRequest = new TransactionRequestDTO(
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
    @DisplayName("Should return transactions of a user")
    public void shouldReturnTransactionsOfUser() throws Exception {
        //GIVEN
        var idUser = 2151L;
        var transactionFirst = new TransactionRequestDTO(idUser, BRL, new BigDecimal(25), USD);
        var transactionSecond = new TransactionRequestDTO(3455L, BRL, new BigDecimal(25), USD);
        var transactionThird = new TransactionRequestDTO(idUser, BRL, new BigDecimal(25), USD);
        var transactionFourth = new TransactionRequestDTO(idUser, BRL, new BigDecimal(25), USD);

        mockMvc.perform(post("/transactions")
                .content(asJson(transactionFirst))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/transactions")
                .content(asJson(transactionSecond))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/transactions")
                .content(asJson(transactionThird))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/transactions")
                .content(asJson(transactionFourth))
                .contentType(MediaType.APPLICATION_JSON));

        //WHEN

        var result = mockMvc.perform(get("/transactions")
                .param("idUser", String.valueOf(idUser))
                .contentType(MediaType.APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));


    }

    @Test
    @DisplayName("Should return all transactions")
    public void shouldReturnAllTransactionsOfUser() throws Exception {
        //GIVEN
        var transactionFirst = new TransactionRequestDTO(5855L, BRL, new BigDecimal(25), USD);
        var transactionSecond = new TransactionRequestDTO(3455L, BRL, new BigDecimal(25), USD);
        var transactionThird = new TransactionRequestDTO(558L, BRL, new BigDecimal(25), USD);
        var transactionFourth = new TransactionRequestDTO(78514L, BRL, new BigDecimal(25), USD);

        mockMvc.perform(post("/transactions")
                .content(asJson(transactionFirst))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/transactions")
                .content(asJson(transactionSecond))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/transactions")
                .content(asJson(transactionThird))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/transactions")
                .content(asJson(transactionFourth))
                .contentType(MediaType.APPLICATION_JSON));

        //WHEN

        var result = mockMvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));


    }


}
