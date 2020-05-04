package sch.frog.learn.spring.reactor.service;

import sch.frog.learn.spring.reactor.model.RCoffee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface R2DBCCoffeeService {
    Flux<Boolean> initCache();

    Mono<RCoffee> findOneCoffee(String name);
}
