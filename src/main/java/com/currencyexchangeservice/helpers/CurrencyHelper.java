package com.currencyexchangeservice.helpers;

import com.currencyexchangeservice.domain.CurrencyRateSnap;
import com.currencyexchangeservice.service.CurrencyRateSnapService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CurrencyHelper {

    public static Mono<CurrencyRateSnap> findCurrencyPairRate(String firstCurrency, String secondCurrency, CurrencyRateSnapService service) {
        Mono<CurrencyRateSnap> firstPair = service.getCurrencyRateSnap(firstCurrency).next();
        return firstPair.map(a -> {
            a.rates.keySet().removeIf(k->!k.equalsIgnoreCase(secondCurrency));
            return a;
        });
    }

    public static List<String> extractKeys(String jsonString) {
        List<String> keys = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            Iterator<String> fieldNames = jsonNode.fieldNames();

            while (fieldNames.hasNext()) {
                keys.add(fieldNames.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keys;
    }
}
