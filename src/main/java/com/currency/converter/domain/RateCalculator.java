package com.currency.converter.domain;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Map;

public class RateCalculator {

    public BigDecimal calculate(Currency origin,
                                 Currency destination,
                                 Map<Currency,@DecimalMin(value = "0.0", inclusive = false) BigDecimal> rates){
        BigDecimal rateOrigin = rates.get(origin);
        BigDecimal rateDestination = rates.get(destination);

        return rateOrigin.divide(rateDestination,MathContext.DECIMAL64);
    }

}
