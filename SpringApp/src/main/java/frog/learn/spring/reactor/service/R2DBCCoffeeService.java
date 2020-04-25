package frog.learn.spring.reactor.service;

import frog.learn.spring.jpademo.model.Coffee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface R2DBCCoffeeService {
    Flux<Boolean> initCache();

    Mono<Coffee> findOneCoffee(String name);
}
