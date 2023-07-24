#REST API of Currency Exchange Rate

This repository contains the source code for the Webflix REST API service. 
The service is built using Spring Boot and offers endpoints to retrieve 
currency exchange rate snapshots and currency information.

#Introduction
Currency Exchange Service REST API provides real-time currency exchange rate snapshots and currency information. It leverages the Spring Boot framework and is designed to be a reactive web service, allowing for efficient handling of concurrent requests.

#Requirements
Java 11 or higher
Spring Boot
Reactor Core
Maven (or any other build tool compatible with Spring Boot projects)

#Endpoints
The following endpoints are available in the API service:

GET /v1/rates: Get all currency exchange rate snapshots.
GET /v1/rates/{base}: Get the currency exchange rate snapshot for the specified base currency.
GET /v1/rates/{firstCurrency}/{secondCurrency}: Get the currency exchange rate snapshot for the specified currency pair.
GET /v1/rates/currencies: Get a list of all supported currencies.


#Disclaimer
Current service using https://openexchangerates.org API information,
for simplicity - no history of ratings is saved, currently application
updating information every 150 seconds (first update directly at the start).

Unfortunately there is not enough exception handling and auto testing in this version.
Main goal of this application to update database and handle users requests in non-blocking way.
