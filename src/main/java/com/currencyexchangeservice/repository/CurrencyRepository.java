package com.currencyexchangeservice.repository;

import com.currencyexchangeservice.domain.Currency;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CurrencyRepository extends ReactiveMongoRepository<Currency,String> {
}
