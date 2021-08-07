package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.currency_exchange.DataSet;
import com.roki.purchase.currency_exchange.LTCube;
import com.roki.purchase.entity.ExchangeRateEntity;
import com.roki.purchase.repository.ExchangeRateRepository;
import com.roki.purchase.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(path = "/web")
public class ExchangeRateController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;


    @Scheduled(cron = "0 0 1 * * *", zone = "Europe/Istanbul")
    public void saveFXRate() {


        CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(restTemplate);

        DataSet dataSet = currencyExchangeService.retrieveCurrencyExchange();
        LTCube ltCube = dataSet.getBody().getCube().get(0);
        List<LTCube.Rate> dataBaseRates = ltCube.getRate();

        System.out.println(dataBaseRates);

        for (int i = 0; i < dataBaseRates.size(); i++) {

            ExchangeRateEntity exchangeRate = exchangeRateRepository.findByCurrencyName(dataBaseRates.get(i).getCurrency());
//            exchangeRate.setCurrencyName(dataBaseRates.get(i).getCurrency());

            exchangeRate.setMultiplier(dataBaseRates.get(i).getMultiplier());

            exchangeRate.setValue(dataBaseRates.get(i).getValue());

            exchangeRateRepository.save(exchangeRate);
        }

        ExchangeRateEntity exchangeRateRON = exchangeRateRepository.findByCurrencyName("RON");

//        exchangeRateRON.setCurrencyName("RON");
        exchangeRateRON.setMultiplier(null);
        exchangeRateRON.setValue(BigDecimal.valueOf(1));
        exchangeRateRepository.save(exchangeRateRON);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/currency/list")
    public ModelAndView getAllCurrency(){
        ModelAndView modelAndView = new ModelAndView("/dashboard/currency/currencies");
        modelAndView.addObject(
                "currencyList",exchangeRateRepository.findAll());
        return modelAndView;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/currency/save")
    public ModelAndView saveCurrency(@ModelAttribute("currencyObject") ExchangeRateEntity currency){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/currency/list");
        exchangeRateRepository.save(currency);
        return modelAndView;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/currency/edit/{currencyId}")
    public ModelAndView editCurrency(@PathVariable Integer currencyId){
        ModelAndView modelAndView = new ModelAndView("/dashboard/currency/currency-form");
        modelAndView.addObject("currencyObject",exchangeRateRepository.findById(currencyId).get());
        return modelAndView;
    }


}
