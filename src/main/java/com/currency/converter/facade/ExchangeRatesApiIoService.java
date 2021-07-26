package com.currency.converter.facade;

import com.currency.converter.domain.Currency;
import com.currency.converter.facade.dto.ResponseExchangeRatesApiIoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRatesApiIoService implements ExchangeRateFacade {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${currencyConverter.exchangeRatesApiIo.accessKey}")
    private String accessKey;

    @Value("${currencyConverter.exchangeRatesApiIo.url}")
    private String url;

    @Override
    public Map<Currency, BigDecimal> getRates() {
        Map<Currency, BigDecimal> returnRates = new HashMap<>();

        var response = restTemplate.getForEntity(url.replace("{access_key}", accessKey), ResponseExchangeRatesApiIoDTO.class);
        Map<String, BigDecimal> rates = response.getBody().getRates();

        rates.entrySet()
                .stream()
                .forEach(entry -> returnRates.put(Currency.valueOf(entry.getKey()), entry.getValue()));

        return returnRates;
    }
}
