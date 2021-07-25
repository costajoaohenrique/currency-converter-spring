package com.currency.converter.dto;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.Transaction;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {

    private Long idTransaction;

    @NotNull
    private Long idUser;

    @NotNull
    private Currency originCurrency;

    @NotNull
    private BigDecimal originalValue;

    @NotNull
    private Currency destinationCurrency;

    @NotNull
    private BigDecimal convertionRate;

    @NotNull
    private LocalDateTime transactionDate;

    @NotNull
    private BigDecimal destinationValue;

    public TransactionResponseDTO(Transaction transaction) {
        this.idTransaction = transaction.getId();
        this.idUser = transaction.getIdUser();
        this.originCurrency = transaction.getOriginCurrency();
        this.originalValue = transaction.getOriginalValue();
        this.destinationCurrency = transaction.getDestinationCurrency();
        this.convertionRate = transaction.getConversionRate();
        this.transactionDate = transaction.getTransactionDate();
        this.destinationValue = transaction.getDestinationValue();
    }
}
