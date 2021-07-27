package com.currency.converter.dto;

import com.currency.converter.domain.Currency;
import com.currency.converter.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotNull
    private Long idUser;

    @NotNull
    private Currency originCurrency;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal originalValue;

    @NotNull
    private Currency destinationCurrency;


    public TransactionRequestDTO(
            @NotNull Long idUser,
            @NotNull Currency originCurrency,
            @NotNull BigDecimal originalValue,
            @NotNull Currency destinationCurrency) {
        this.idUser = idUser;
        this.originCurrency = originCurrency;
        this.originalValue = originalValue;
        this.destinationCurrency = destinationCurrency;
    }

}
