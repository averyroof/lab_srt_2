package com.example.demo.controllers;

import com.example.demo.services.CalculateDispersionService;
import com.example.demo.services.ExchangeRatesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class ExchangeRatesController {

    final ExchangeRatesService exchangeRatesService;
    final CalculateDispersionService calculateDispersionService;

    public ExchangeRatesController(ExchangeRatesService exchangeRatesService, CalculateDispersionService calculateDispersionService) {
        this.exchangeRatesService = exchangeRatesService;
        this.calculateDispersionService = calculateDispersionService;
    }

    @GetMapping("/rate/{date}")
    public Mono<String> rate(@PathVariable() String date) {
        return exchangeRatesService.getUSD(date);
    }

    @GetMapping("/dispersion/{since}")
    public Mono<Float> dispersion(@PathVariable() String since) {
        return exchangeRatesService.getHistoryUSD(since)
                .flatMap(s -> Mono.just(calculateDispersionService.parsingData(s)).subscribeOn(Schedulers.parallel()))
                .flatMap(floats -> Mono.just(calculateDispersionService.dispersion(floats)).publishOn(Schedulers.parallel()))
                .subscribeOn(Schedulers.parallel());
    }
}
