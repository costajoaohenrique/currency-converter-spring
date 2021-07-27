package com.currency.converter.repositories;

import com.currency.converter.domain.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static com.currency.converter.domain.Currency.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("Should save transaction")
    public void shouldSaveTransaction() {
        var idUser = 15252L;
        var originCurrency = BRL;
        var originalValue = new BigDecimal(25);
        var destinationCurrency = USD;
        var conversionRate = new BigDecimal("0.19140121");
        //WHEN
        var transaction = new Transaction(idUser, originCurrency, originalValue, destinationCurrency, conversionRate);
        var transactionSaved = repository.save(transaction);
        //THEN
        assertNotNull(transactionSaved.getId());
    }

    @Test
    @DisplayName("Should get transaction by user id")
    public void shouldGetTransactionByUserId() {
        var idUser = 15252L;

        var transactionFirst = new Transaction(idUser, BRL, new BigDecimal(25), USD, new BigDecimal("0.19140121"));
        var transactionSecond = new Transaction(55815L, BRL, new BigDecimal(78), JPY, new BigDecimal("0.58479557"));
        var transactionThird = new Transaction(idUser, BRL, new BigDecimal(32), USD, new BigDecimal("0.17872128"));
        var transactionFourth = new Transaction(16324L, USD, new BigDecimal(32), EUR, new BigDecimal("0.19522444"));
        //WHEN
        repository.save(transactionFirst);
        repository.save(transactionSecond);
        repository.save(transactionThird);
        repository.save(transactionFourth);
        var transactionsFoundByIdUser = repository.findByIdUser(idUser);
        //THEN
        assertTrue(transactionsFoundByIdUser.size() == 2);
    }


}
