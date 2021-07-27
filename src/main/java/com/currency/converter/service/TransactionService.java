package com.currency.converter.service;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.ConversionRateCalculator;
import com.currency.converter.domain.Transaction;
import com.currency.converter.dto.TransactionRequestDTO;
import com.currency.converter.dto.TransactionResponseDTO;
import com.currency.converter.facade.ExchangeRateFacade;
import com.currency.converter.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        var rates = exchangeRateFacade.getRates();
        var conversionRate = conversionRateCalculator.calculate(transactionRequestDTO.getOriginCurrency(),
                transactionRequestDTO.getDestinationCurrency(), rates);

        Transaction transaction;
        transaction = Transaction.createTransaction(transactionRequestDTO, conversionRate);
        transaction = repository.save(transaction);

        return new TransactionResponseDTO(transaction);
    }

    public List<TransactionResponseDTO> findByIdUser(Long idUser){
        return repository.findByIdUser(idUser)
                .stream()
                .map(transaction -> new TransactionResponseDTO(transaction))
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> findAll (){
        return repository.findAll()
                .stream()
                .map(transaction -> new TransactionResponseDTO(transaction))
                .collect(Collectors.toList());
    }

}
