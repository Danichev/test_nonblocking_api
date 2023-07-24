package com.currencyexchangeservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRateSnap {

    @Id
    private String id;

    private String base;

    @JsonIgnore
    public HashMap<String, BigDecimal> rates;
    private Date timestamp;
    @JsonProperty("rates")
    public TreeMap<String, BigDecimal> getRates() {
        TreeMap<String, BigDecimal> sortedRates =  new TreeMap<>(this.rates);
        return sortedRates;
    }

    @JsonProperty("rates")
    public void setRates(HashMap<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
