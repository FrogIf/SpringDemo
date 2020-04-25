package frog.learn.spring.reactor.service;

import frog.learn.spring.jpademo.model.CoffeeOrder;
import reactor.core.publisher.Mono;

public interface R2DBCOrderService {
    Mono<Long> create(CoffeeOrder order);
}
