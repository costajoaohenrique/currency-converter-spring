package com.currency.converter.facade.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ResponseExchangeRatesApiIoDTO {
        private boolean success;
        private int timestamp;
        private String base;
        private String date;
        private Map<String, BigDecimal> rates;
}
