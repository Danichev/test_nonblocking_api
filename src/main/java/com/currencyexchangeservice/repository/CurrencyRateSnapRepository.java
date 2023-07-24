package com.currencyexchangeservice.repository;

import com.currencyexchangeservice.domain.CurrencyRateSnap;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CurrencyRateSnapRepository extends ReactiveMongoRepository<CurrencyRateSnap,String> {

    Flux<CurrencyRateSnap> findByBase(String base);
}
