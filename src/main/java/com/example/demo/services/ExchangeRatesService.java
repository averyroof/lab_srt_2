package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ExchangeRatesService {

    //    public HttpClient client = HttpClient.newHttpClient();
    public WebClient webClient = WebClient.create("https://api.exchangeratesapi.io/");

    public Mono<String> getUSD(String date) {
//        String todayDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
//        HttpRequest req = HttpRequest.newBuilder(URI.create("https://api.exchangeratesapi.io/history?start_at=" + date +
//                "&end_at=" + todayDate)).GET().build();
//        try {
//            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
//            return response.body();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return null;
//        }
        return webClient.get().uri(date + "?symbols=USD").retrieve().bodyToMono(String.class);
    }

    public Mono<String> getHistoryUSD(String date) {
        String todayDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        return webClient.get().uri("history?start_at=" + date + "&end_at=" + todayDate + "&symbols=USD").retrieve()
                .bodyToMono(String.class);
    }
}
