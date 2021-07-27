package com.currency.converter.controllers;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.Transaction;
import com.currency.converter.dto.TransactionRequestDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static com.currency.converter.controllers.JsonUtils.asJson;
import static com.currency.converter.domain.Currency.BRL;
import static com.currency.converter.domain.Currency.USD;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled("Run these tests only with the network connection disabled")
public class ConnectApiExchangeErrorHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return bad gateway when there is no connection with the api")
    public void shouldReturnBadGateway() throws Exception {
        //GIVEN
        var transactionRequest = new TransactionRequestDTO(222L, BRL, new BigDecimal(25), USD);
        //WHEN
        ResultActions result = mockMvc.perform(post("/transactions")
                .content(asJson(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON));
        //THEN
        result.andExpect(status().isBadGateway());

    }

    @Test
    @DisplayName("Should return details message when there is no connection with the api")
    public void shoudReturnDetailsMessage() throws Exception {
        //GIVEN
        var transactionRequest = new TransactionRequestDTO(222L, BRL, new BigDecimal(25), USD);
        //WHEN
        ResultActions result = mockMvc.perform(post("/transactions")
                .content(asJson(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON));
        //THEN
        result.andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.status").value(BAD_GATEWAY.value()))
                .andExpect(jsonPath("$.title").value("Bad Gateway"))
                .andExpect(jsonPath("$.detail").value("Unable to connect to exchange server, please try again later"));

    }


}
