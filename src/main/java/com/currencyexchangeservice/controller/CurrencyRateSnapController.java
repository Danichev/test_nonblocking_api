package com.currencyexchangeservice.controller;

import com.currencyexchangeservice.domain.Currency;
import com.currencyexchangeservice.domain.CurrencyRateSnap;
import com.currencyexchangeservice.helpers.CurrencyHelper;
import com.currencyexchangeservice.service.CurrencyRateSnapService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/v1")
public class CurrencyRateSnapController {

    private CurrencyRateSnapService currencyRateSnapService;

    public CurrencyRateSnapController(CurrencyRateSnapService currencyRateSnapService) {
        this.currencyRateSnapService = currencyRateSnapService;
    }

    @GetMapping("/rates")
    public Flux<CurrencyRateSnap> getAllCurrencyRateSnap (){

        return currencyRateSnapService.getAllCurrencyRateSnap();
    }

    @GetMapping("/rates/{base}")
    public Mono<CurrencyRateSnap> getCurrencyRateSnap (@PathVariable String base){

        return currencyRateSnapService.getCurrencyRateSnap(base).next();
    }

    @GetMapping("/rates/{firstCurrency}/{secondCurrency}")
    public Mono<CurrencyRateSnap> getCurrencyRateSnap (@PathVariable String firstCurrency, @PathVariable String secondCurrency){
        return CurrencyHelper.findCurrencyPairRate(firstCurrency,secondCurrency,currencyRateSnapService);
    }

    @GetMapping("/rates/currencies")
    public Flux<Currency> getAllCurrencies (){

        return currencyRateSnapService.getCurrenciesList();
    }
}
