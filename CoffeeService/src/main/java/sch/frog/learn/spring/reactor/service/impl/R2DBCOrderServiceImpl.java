package sch.frog.learn.spring.reactor.service.impl;

import sch.frog.learn.spring.reactor.model.RCoffeeOrder;
import sch.frog.learn.spring.reactor.repository.R2DBCCoffeeOrderRepository;
import sch.frog.learn.spring.reactor.service.R2DBCOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class R2DBCOrderServiceImpl implements R2DBCOrderService {

    @Autowired
    private R2DBCCoffeeOrderRepository r2DBCCoffeeOrderRepository;

    @Override
    public Mono<Long> create(RCoffeeOrder order){
        return r2DBCCoffeeOrderRepository.save(order);
    }

}
