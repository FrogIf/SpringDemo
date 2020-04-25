package frog.learn.spring.client;

import frog.learn.spring.jpademo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

/*
 * 使用web client 进行响应式编程
 */
@Component
@Configuration
@Slf4j
public class WebClientDemoRunner implements ApplicationRunner {

    @Autowired
    private WebClient client;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CountDownLatch cdl = new CountDownLatch(2);

        client.get()
                .uri("/coffee/{id}", 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve() // ---查询---
                .bodyToMono(Coffee.class)
                .doOnError(e -> log.info("error :", e))
                .doFinally(s -> cdl.countDown())
                .subscribeOn(Schedulers.single())
                .subscribe(c -> log.info("Coffee by id : {}", c));

        Mono<Coffee> coffeeMono = Mono.just(Coffee.builder()
                .name("erer")
                .price(Money.of(CurrencyUnit.of("CNY"), 25.00))
                .build()
        );

        client.post()
                .uri("/coffee/")
                .body(coffeeMono, Coffee.class)
                .retrieve()
                .bodyToMono(Coffee.class)
                .doFinally(s -> cdl.countDown())
                .subscribeOn(Schedulers.single())
                .subscribe(c -> log.info("Coffee Created : {}", c));

        cdl.await();

        client.get()
                .uri("/coffee/")
                .retrieve()
                .bodyToFlux(Coffee.class)
                .toStream() // 这个是阻塞的
                .forEach(c -> log.info("Coffee in List : {}", c));


    }

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl("http://localhost:8080").build();
    }
}
