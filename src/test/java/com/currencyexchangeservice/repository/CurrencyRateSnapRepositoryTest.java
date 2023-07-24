package com.currencyexchangeservice.repository;

import com.currencyexchangeservice.domain.CurrencyRateSnap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest (excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class CurrencyRateSnapRepositoryTest {
    @Autowired
    CurrencyRateSnapRepository currencyRateSnapRepository;



    @BeforeEach
    void setUp() {
        HashMap<String, BigDecimal> ratesMap = new HashMap<>();
        ratesMap.put("PLN", new BigDecimal("5.0"));
        ratesMap.put("EUR", new BigDecimal("0.85"));
        ratesMap.put("JPY", new BigDecimal("110.5"));

        Date timestamp =  new Date();

        CurrencyRateSnap cur1 = new CurrencyRateSnap(null, "USD", ratesMap, timestamp);
        CurrencyRateSnap cur2 = new CurrencyRateSnap(null, "TRR", ratesMap, timestamp);
        CurrencyRateSnap cur3 = new CurrencyRateSnap(null, "KLN", ratesMap, timestamp);

        var currencies = List.of(cur1,cur2,cur3);
        currencyRateSnapRepository.saveAll(currencies).blockLast();
    }

    @AfterEach
    void tearDown(){
        currencyRateSnapRepository.deleteAll().block();
    }
    @Test
    void findAll() {

        var currencyRateFlux = currencyRateSnapRepository.findAll();

        StepVerifier.create(currencyRateFlux)
                .expectNextCount(3)
                .verifyComplete();
    }
}