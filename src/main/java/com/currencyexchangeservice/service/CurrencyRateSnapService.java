package com.currencyexchangeservice.service;

import com.currencyexchangeservice.domain.Currency;
import com.currencyexchangeservice.domain.CurrencyRateSnap;
import com.currencyexchangeservice.helpers.CurrencyHelper;
import com.currencyexchangeservice.repository.CurrencyRateSnapRepository;
import com.currencyexchangeservice.repository.CurrencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.util.List;

@Service
public class CurrencyRateSnapService {

    private CurrencyRateSnapRepository currencyRateSnapRepository;
    private CurrencyRepository currencyRepository;

    public CurrencyRateSnapService(CurrencyRateSnapRepository currencyRateSnapRepository, CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyRateSnapRepository = currencyRateSnapRepository;
    }


    public void addCurrencyRateSnap(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        CurrencyRateSnap currencyRateSnap;
        try {
            currencyRateSnap = objectMapper.readValue(response, CurrencyRateSnap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        currencyRateSnapRepository.save(currencyRateSnap).subscribe();
    }

    public Flux<CurrencyRateSnap> getAllCurrencyRateSnap() {
        return currencyRateSnapRepository.findAll();
    }

    public Flux<CurrencyRateSnap> getCurrencyRateSnap(String base) {
        return currencyRateSnapRepository.findByBase(base);
    }

    public void addCurrency(Currency currency) {
        currencyRepository.save(currency).subscribe();
    }

    public Flux<Currency> getCurrenciesList() {
        return currencyRepository.findAll();
    }


    public void updateCurrencyList(String response) {
        currencyRepository.deleteAll().subscribe();
        List<String> currencies = CurrencyHelper.extractKeys(response);
        currencies.forEach(currency->
        addCurrency(new Currency(null,currency)));
    }

    public void deleteRates() {
        currencyRateSnapRepository.deleteAll().subscribe();
    }
}
