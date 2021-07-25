package com.currency.converter.service;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.ConversionRateCalculator;
import com.currency.converter.domain.Transaction;
import com.currency.converter.dto.TransactionRequestDTO;
import com.currency.converter.dto.TransactionResponseDTO;
import com.currency.converter.facade.ExchangeRateFacade;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionService {

    private ExchangeRateFacade exchangeRateFacade;

    private ConversionRateCalculator conversionRateCalculator;

    public TransactionService(ExchangeRateFacade exchangeRateFacade,
                              ConversionRateCalculator conversionRateCalculator) {
        this.exchangeRateFacade = exchangeRateFacade;
        this.conversionRateCalculator = conversionRateCalculator;
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO){
        Map<Currency, BigDecimal> rates = exchangeRateFacade.getRates();
        BigDecimal convertionRate = conversionRateCalculator.calculate(transactionRequestDTO.getOriginCurrency(),
                transactionRequestDTO.getDestinationCurrency(), rates);

        Transaction transaction = Transaction.createTransaction(transactionRequestDTO, convertionRate);
        transaction.setId(1l);

        return new TransactionResponseDTO(transaction);
    }

}
