package com.currency.converter.facade;

import com.currency.converter.domain.Currency;

import java.math.BigDecimal;
import java.util.Map;

public interface ExchangeRateFacade {

    public Map<Currency, BigDecimal> getRates();
}
