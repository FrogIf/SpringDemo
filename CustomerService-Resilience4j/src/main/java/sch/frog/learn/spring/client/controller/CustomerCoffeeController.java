package sch.frog.learn.spring.client.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sch.frog.learn.spring.client.integration.CoffeeOrderService;
import sch.frog.learn.spring.client.integration.CoffeeService;
import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.web.request.NewOrderRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("customer")
@Slf4j
public class CustomerCoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    private CircuitBreaker circuitBreaker;

    public CustomerCoffeeController(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("menu");
    }

    @GetMapping("/menu")
    public List<Coffee> readMenu(){
        return Try.ofSupplier(
                    CircuitBreaker.decorateSupplier(this.circuitBreaker, ()->coffeeService.findAll())
                )
                .recover(CircuitBreakerOpenException.class, Collections.emptyList())
                .get();
    }

    @PostMapping("/order")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "order")
    public CoffeeOrder createOrder(){
        NewOrderRequest orderRequest = NewOrderRequest.builder()
                .customer("frog")
                .items(Arrays.asList("latte"))
                .build();
        CoffeeOrder coffeeOrder = coffeeOrderService.create(orderRequest);

        log.info("Order ID : {}", coffeeOrder != null ? coffeeOrder.getId() : "-");
        return coffeeOrder;
    }

    public CoffeeOrder createOrderFallBack(){
        log.warn("fall to null order.");
        return null;
    }

}
