package com.currency.converter.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
public class Transaction {

    private Long id;

    private Long idUser;

    private Currency originCurrency;

    private BigDecimal originalValue;

    private Currency destinationCurrency;

    private BigDecimal convertionRate;

    private LocalDateTime transactionDate;

    private BigDecimal destinationValue;

    public Transaction(Long idUser,
                       Currency originCurrency,
                       BigDecimal originalValue,
                       Currency destinationCurrency,
                       BigDecimal convertionRate) {
        this.idUser = idUser;
        this.originCurrency = originCurrency;
        this.originalValue = originalValue;
        this.destinationCurrency = destinationCurrency;
        this.convertionRate = convertionRate;
        this.transactionDate = LocalDateTime.now();
        this.destinationValue = convertToDestinationValue();
    }

    private BigDecimal convertToDestinationValue(){
        return originalValue.multiply(convertionRate);
    }

}
