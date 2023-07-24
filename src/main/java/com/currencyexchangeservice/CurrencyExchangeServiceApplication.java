package com.currencyexchangeservice;

import com.currencyexchangeservice.controller.DataApiFetcher;
import com.currencyexchangeservice.service.CurrencyRateSnapService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CurrencyExchangeServiceApplication {

	public static void main(String[] args) {
		ApplicationContext ctx =SpringApplication.run(CurrencyExchangeServiceApplication.class, args);

		CurrencyRateSnapService currencyRateSnapService = (CurrencyRateSnapService) ctx.getBean("currencyRateSnapService");
		DataApiFetcher rateFetcher = new DataApiFetcher(currencyRateSnapService);

		rateFetcher.firstFetch();
		rateFetcher.startFetchingRates();
	}

}
