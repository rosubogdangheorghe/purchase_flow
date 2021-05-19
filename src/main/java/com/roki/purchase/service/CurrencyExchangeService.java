package com.roki.purchase.service;

import com.roki.purchase.currency_exchange.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Scheduled
//@EnableScheduling

@Service
public class CurrencyExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeService.class);
    private static final String CURRENCY_EXCHANGE_RATE_API_URL = "https://www.bnro.ro/nbrfxrates.xml";

    private RestTemplate restTemplate;
    private DataSet dataSet;

    @Autowired
    public CurrencyExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        retrieveCurrencyExchange();
        LOGGER.info(getClass().getSimpleName() + "created");

    }

    public DataSet retrieveCurrencyExchange() {
        try {
            dataSet = this.restTemplate.getForObject(CURRENCY_EXCHANGE_RATE_API_URL,DataSet.class);

        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());
        }
        return dataSet;
    }

    @Override
    public String toString() {
        return "CurrencyExchangeService{" +
                "restTemplate=" + restTemplate +
                ", dataSet=" + dataSet +
                '}';
    }
}
