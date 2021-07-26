package com.currency.converter.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ConversionRateCalculator {

    public BigDecimal calculate(Currency origin,
                                 Currency destination,
                                 Map<Currency,@DecimalMin(value = "0.0", inclusive = false) BigDecimal> rates){
        var rateOrigin = rates.get(origin);
        var rateDestination = rates.get(destination);

        return rateOrigin.divide(rateDestination,MathContext.DECIMAL64);
    }

}
