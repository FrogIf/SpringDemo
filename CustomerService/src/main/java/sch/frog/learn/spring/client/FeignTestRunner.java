package sch.frog.learn.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sch.frog.learn.spring.client.integration.CoffeeOrderService;
import sch.frog.learn.spring.client.integration.CoffeeService;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.web.request.NewOrderRequest;

import java.util.Arrays;

@Component
@Slf4j
@Order(2)
public class FeignTestRunner implements ApplicationRunner {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("=========================");
        coffeeService.findAll().forEach(c -> log.info("Coffee : {}", c));

        NewOrderRequest orderRequest = NewOrderRequest.builder()
                .customer("uiui")
                .items(Arrays.asList("capuccino"))
                .build();

        CoffeeOrder coffeeOrder = coffeeOrderService.create(orderRequest);
        log.info("Order : {}", coffeeOrder);

        CoffeeOrder order = coffeeOrderService.getOrder(coffeeOrder.getId());
        log.info("Order : {}", order);
    }

}
