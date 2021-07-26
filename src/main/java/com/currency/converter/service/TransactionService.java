package com.currency.converter.service;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.ConversionRateCalculator;
import com.currency.converter.domain.Transaction;
import com.currency.converter.dto.TransactionRequestDTO;
import com.currency.converter.dto.TransactionResponseDTO;
import com.currency.converter.facade.ExchangeRateFacade;
import com.currency.converter.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionService {

    private ExchangeRateFacade exchangeRateFacade;

    private ConversionRateCalculator conversionRateCalculator;

    private TransactionRepository repository;

    public TransactionService(ExchangeRateFacade exchangeRateFacade,
                              ConversionRateCalculator conversionRateCalculator,
                              TransactionRepository repository) {
        this.exchangeRateFacade = exchangeRateFacade;
        this.conversionRateCalculator = conversionRateCalculator;
        this.repository = repository;
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        var rates = exchangeRateFacade.getRates();
        var conversionRate = conversionRateCalculator.calculate(transactionRequestDTO.getOriginCurrency(),
                transactionRequestDTO.getDestinationCurrency(), rates);

        var transaction = Transaction.createTransaction(transactionRequestDTO, conversionRate);
        transaction = repository.save(transaction);

        return new TransactionResponseDTO(transaction);
    }

}
