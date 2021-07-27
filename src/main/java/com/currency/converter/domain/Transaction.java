package com.currency.converter.domain;

import com.currency.converter.dto.TransactionRequestDTO;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "TB_TRANSACTION")
public class Transaction {

    @Id  @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long idUser;

    @Enumerated(value = EnumType.STRING)
    private Currency originCurrency;

    private BigDecimal originalValue;

    @Enumerated(value = EnumType.STRING)
    private Currency destinationCurrency;

    private BigDecimal conversionRate;

    private LocalDateTime transactionDate;

    @Transient
    private BigDecimal destinationValue;

    public Transaction(){};

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
