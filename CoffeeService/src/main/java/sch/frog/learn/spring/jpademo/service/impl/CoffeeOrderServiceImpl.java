package sch.frog.learn.spring.jpademo.service.impl;

import org.springframework.messaging.support.MessageBuilder;
import sch.frog.learn.spring.common.constant.CommonConstant;
import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.entity.OrderState;
import sch.frog.learn.spring.jpademo.dao.CoffeeOrderRepository;
import sch.frog.learn.spring.jpademo.dao.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sch.frog.learn.spring.jpademo.service.CoffeeOrderService;
import sch.frog.learn.spring.mq.Barista;
import sch.frog.learn.spring.support.OrderProperties;

import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private OrderProperties orderProperties;

    private String waiterId = UUID.randomUUID().toString();

    @Autowired
    private Barista barista;

    @Override
    @Transactional
    public CoffeeOrder createOrder(String customer, List<Coffee> coffees){
        List<Coffee> cofs = coffees.stream().filter(Objects::nonNull).collect(Collectors.toList());
        CoffeeOrder order = CoffeeOrder.builder().customer(customer)
                .items(new ArrayList<>(cofs)) // 必须再包一层, Arrays.asList生成的是不可编辑的List, 再updateOrder时, 会调用remove方法, 导致异常
                .state(OrderState.INIT)
                .discount(orderProperties.getDiscount())
                .total(calcTotal(coffees))
                .waiter(orderProperties.getWaiterPrefix() + waiterId)
                .build();
        CoffeeOrder save = coffeeOrderRepository.save(order);
        log.info("new order : {}", save);
        return save;
    }

    @Override
    public boolean updateState(CoffeeOrder order, OrderState state){
        if(state.compareTo(order.getState()) <= 0){
            log.warn("Wrong State order : {}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        log.info("Update Order : {}", order);
        if(state == OrderState.PAID){
            barista.newOrder().send(MessageBuilder.withPayload(order.getId()).build());
        }
        return true;
    }

    @Override
    public List<CoffeeOrder> findAllByCustomer(String customer) {
        return coffeeOrderRepository.findByCustomerOrderById(customer);
    }

    @Override
    public CoffeeOrder get(Long id) {
        return coffeeOrderRepository.findById(id).orElse(null);
//        return coffeeOrderRepository.getOne(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void initOrders() {
        // 创建coffee
        Coffee latte = Coffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 30.0))
                .build();
        log.info("coffee : {}", latte);

        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 20.0))
                .build();
        log.info("coffee : {}", espresso);

        coffeeRepository.save(latte);
        coffeeRepository.save(espresso);

        // 创建订单
        CoffeeOrder orderA = CoffeeOrder.builder()
                .customer("frog")
                .items(Collections.singletonList(espresso))
                .state(OrderState.INIT)
                .build();
        log.info("order : {}", orderA);

        CoffeeOrder orderB = CoffeeOrder.builder()
                .customer("snail")
                .items(Arrays.asList(espresso, latte))
                .state(OrderState.INIT)
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

    private Money calcTotal(List<Coffee> coffees){
        List<Money> monies = coffees.stream().map(Coffee::getPrice)
                .collect(Collectors.toList());
        return Money.total(monies).multipliedBy(orderProperties.getDiscount())
                .dividedBy(100, RoundingMode.HALF_UP);

    }
}
