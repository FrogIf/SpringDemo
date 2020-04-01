package frog.learn.spring.jpademo.service.impl;

import frog.learn.spring.jpademo.dao.CoffeeOrderRepository;
import frog.learn.spring.jpademo.dao.CoffeeRepository;
import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.jpademo.model.CoffeeOrder;
import frog.learn.spring.jpademo.model.OrderState;
import frog.learn.spring.jpademo.service.CoffeeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void initOrders() {
        // 创建coffee
        Coffee latte = Coffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .build();
        log.info("coffee : {}", latte);

        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();
        log.info("coffee : {}", espresso);

        coffeeRepository.save(latte);
        coffeeRepository.save(espresso);

        // 创建订单
        CoffeeOrder orderA = CoffeeOrder.builder()
                .customer("frog")
                .items(Collections.singletonList(espresso))
                .orderState(OrderState.INIT)
                .build();
        log.info("order : {}", orderA);

        CoffeeOrder orderB = CoffeeOrder.builder()
                .customer("snail")
                .items(Arrays.asList(espresso, latte))
                .orderState(OrderState.INIT)
                .build();
        log.info("order : {}", orderB);

        coffeeOrderRepository.save(orderA);
        coffeeOrderRepository.save(orderB);
    }

    @Override
    public void findOrders() {
        coffeeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .forEach(c -> log.info("coffee : {}", c));

        List<CoffeeOrder> top3Orders = coffeeOrderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
        log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}", getJoinedOrderId(top3Orders));

        top3Orders.forEach(o -> {
            log.info("Order : {}", o.getId());
            o.getItems().forEach(i -> log.info("item:{}", i));
        });

        List<CoffeeOrder> latte = coffeeOrderRepository.findByItems_Name("latte");
        log.info("findByItems_Name : {}", getJoinedOrderId(latte));
    }

    private String getJoinedOrderId(List<CoffeeOrder> list){
        return list.stream().map(o -> o.getId().toString()).collect(Collectors.joining(","));
    }
}
