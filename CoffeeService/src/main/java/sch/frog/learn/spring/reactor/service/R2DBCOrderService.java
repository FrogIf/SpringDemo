package sch.frog.learn.spring.reactor.service;

import sch.frog.learn.spring.reactor.model.RCoffeeOrder;
import reactor.core.publisher.Mono;

public interface R2DBCOrderService {
    Mono<Long> create(RCoffeeOrder order);
}
