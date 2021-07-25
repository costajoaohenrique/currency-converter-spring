package com.currency.converter.domain;

import com.currency.converter.dto.TransactionRequestDTO;
import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    private Long id;

    private Long idUser;

    private Currency originCurrency;

    private BigDecimal originalValue;

    private Currency destinationCurrency;

    private BigDecimal conversionRate;

    private LocalDateTime transactionDate;

    private BigDecimal destinationValue;

    public Transaction(Long idUser,
                       Currency originCurrency,
                       BigDecimal originalValue,
                       Currency destinationCurrency,
                       BigDecimal conversionRate) {
        this.idUser = idUser;
        this.originCurrency = originCurrency;
        this.originalValue = originalValue;
        this.destinationCurrency = destinationCurrency;
        this.conversionRate = conversionRate;
        this.transactionDate = LocalDateTime.now();
        this.destinationValue = convertToDestinationValue();
    }

    public static Transaction createTransaction(TransactionRequestDTO transactionDTO, BigDecimal conversionRate) {
        return   new Transaction(transactionDTO.getIdUser(),
                transactionDTO.getOriginCurrency(),
                transactionDTO.getOriginalValue(),
                transactionDTO.getDestinationCurrency(),
                conversionRate);
    }

    private BigDecimal convertToDestinationValue() {
        return originalValue.multiply(conversionRate);
    }

}
