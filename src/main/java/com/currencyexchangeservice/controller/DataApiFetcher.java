package com.currencyexchangeservice.controller;

import com.currencyexchangeservice.domain.Currency;
import com.currencyexchangeservice.service.CurrencyRateSnapService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;


@Component
public class DataApiFetcher {

    private CurrencyRateSnapService currencyRateSnapService;
    private final WebClient webClient;
    private final String apiUrl;
    private final String apiKey;
    private final String currenciesListUrl;
    private Disposable disposable;

    public DataApiFetcher(CurrencyRateSnapService currencyRateSnapService) {
        this.currencyRateSnapService = currencyRateSnapService;
        this.webClient = WebClient.create("https://openexchangerates.org");
        this.apiUrl = "/api/latest.json";
        this.apiKey = "0b738d39e66f439683a28a2931b4bb8b";
        this.currenciesListUrl= "/api/currencies.json";

    }

    public void firstFetch() {
        this.disposable = Mono.defer(this::fetchListOfCurrencies)
                .subscribe();
        currencyRateSnapService.deleteRates();
        this.disposable = currencyRateSnapService.getCurrenciesList().flatMap(this::fetchExchange).subscribe();
    }
    public void startFetchingRates() {
        this.disposable = Mono.delay(Duration.ofSeconds(150))
                .flatMap(delay -> fetchExchangeRates())
                .repeat()
                .subscribe();
    }

    private Mono<Void> fetchListOfCurrencies() {
        return webClient.get()
                .uri(builder -> builder.path(currenciesListUrl)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(this::processCurrencyListUpdate)
                .doOnError(this::handleError)
                .then();
    }

    private Mono<Void> fetchExchange(Currency currency) {
        return webClient.get()
                .uri(builder -> builder.path(apiUrl)
                        .queryParam("app_id", apiKey)
                        .queryParam("base", currency.getName())
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(this::processExchangeRates)
                .doOnError(this::handleError)
                .then();
    }

    private Mono<Void> fetchExchangeRates() {
        return  webClient.get()
                .uri(builder -> builder.path(apiUrl)
                        .queryParam("app_id", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(this::processExchangeRates)
                .doOnError(this::handleError)
                .then();
    }


    private void processExchangeRates(String response) {
        currencyRateSnapService.addCurrencyRateSnap(response);
    }

    private void processCurrencyListUpdate(String response) {
        currencyRateSnapService.updateCurrencyList(response);
    }

    private void handleError(Throwable throwable) {
        System.err.println("Error occurred: " + throwable.getMessage());
    }

}
