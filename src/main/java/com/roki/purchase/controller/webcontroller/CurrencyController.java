package com.roki.purchase.controller.webcontroller;


import com.roki.purchase.entity.CurrencyEntity;
import com.roki.purchase.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping(path = "/web")
public class CurrencyController {

    @Autowired
    private CurrencyRepository currencyRepository;

    @GetMapping("/currency/list")
    public ModelAndView getAllCurrency(){
        ModelAndView modelAndView = new ModelAndView("/dashboard/currency/currencies");
        modelAndView.addObject(
                "currencyList",currencyRepository.findAll());
        return modelAndView;
    }


    @GetMapping("/currency/add")
    public ModelAndView addCurrency(){
        ModelAndView modelAndView = new ModelAndView("/dashboard/currency/currency-form");
        Set<Currency> currencies = getCurrencyByCountry();
        modelAndView.addObject("currencies",currencies);
        modelAndView.addObject("currencyObject",new CurrencyEntity());
        return modelAndView;
    }

   @PostMapping("/currency/save")
    public ModelAndView saveCurrency(@ModelAttribute ("currencyObject") CurrencyEntity currency){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/currency/list");
        currencyRepository.save(currency);
        return modelAndView;
    }

    @GetMapping("/currency/edit/{currencyId}")
    public ModelAndView editCurrency(@PathVariable Integer currencyId){
        ModelAndView modelAndView = new ModelAndView("/dashboard/currency/currency-form");
        modelAndView.addObject("currencyObject",currencyRepository.findById(currencyId).get());
        return modelAndView;
    }


    private Set<Currency> getCurrencyByCountry(){
        Set<Currency> currencies = new HashSet<>();
        currencies = Currency.getAvailableCurrencies();
        return currencies;

    }
}
