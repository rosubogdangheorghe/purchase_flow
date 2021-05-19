package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.currency_exchange.DataSet;
import com.roki.purchase.currency_exchange.LTCube;
import com.roki.purchase.entity.CurrencyEntity;
import com.roki.purchase.entity.ExchangeRateEntity;
import com.roki.purchase.repository.CurrencyExchangeRepository;
import com.roki.purchase.repository.CurrencyRepository;
import com.roki.purchase.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExchangeRateController {


    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Scheduled(cron = "0 0 1 * * *",zone = "Europe/Istanbul")
    public void saveFXRate() {


        CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(restTemplate);

        DataSet dataSet = currencyExchangeService.retrieveCurrencyExchange();
        LTCube ltCube = dataSet.getBody().getCube().get(0);
        List<LTCube.Rate> rates= ltCube.getRate();
        LocalDate date = LocalDate.of(ltCube.getDate().getYear(),ltCube.getDate().getMonth(),ltCube.getDate().getDay());



        ExchangeRateEntity exchangeRateEur = new ExchangeRateEntity();
        CurrencyEntity eur = currencyRepository.findByCurrency(rates.get(10).getCurrency());
        exchangeRateEur.setCurrency(eur);
        exchangeRateEur.setValue(rates.get(10).getValue());
        exchangeRateEur.setExchangeDate(date);

        currencyExchangeRepository.save(exchangeRateEur);

        ExchangeRateEntity exchangeRateUsd = new ExchangeRateEntity();
        CurrencyEntity usd = currencyRepository.findByCurrency(rates.get(28).getCurrency());
        exchangeRateUsd.setCurrency(usd);
        exchangeRateUsd.setValue(rates.get(28).getValue());
        exchangeRateUsd.setExchangeDate(date);

        currencyExchangeRepository.save(exchangeRateUsd);


        ExchangeRateEntity exchangeRateChf = new ExchangeRateEntity();
        CurrencyEntity chf = currencyRepository.findByCurrency(rates.get(5).getCurrency());
        exchangeRateChf.setCurrency(chf);
        exchangeRateChf.setValue(rates.get(5).getValue());
        exchangeRateChf.setExchangeDate(date);

        currencyExchangeRepository.save(exchangeRateChf);

        ExchangeRateEntity exchangeRateGbp = new ExchangeRateEntity();
        CurrencyEntity gbp = currencyRepository.findByCurrency(rates.get(11).getCurrency());
        exchangeRateGbp.setCurrency(gbp);
        exchangeRateGbp.setValue(rates.get(11).getValue());
        exchangeRateGbp.setExchangeDate(date);

        currencyExchangeRepository.save(exchangeRateGbp);



//        for (LTCube.Rate rate:rates) {
//            System.out.println(rate);
//        }
//
//       String currency =  rates.get(10).getCurrency();
//        BigDecimal value = rates.get(10).getValue();
//        System.out.println(currency);
//        System.out.println(value);
//        System.out.println(date);

    }



}
