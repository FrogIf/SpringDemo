package frog.learn.spring.reactor.service.impl;

import frog.learn.spring.jpademo.model.CoffeeOrder;
import frog.learn.spring.reactor.repository.R2DBCCoffeeOrderRepository;
import frog.learn.spring.reactor.service.R2DBCOrderService;
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
    public Mono<Long> create(CoffeeOrder order){
        return r2DBCCoffeeOrderRepository.save(order);
    }

}
