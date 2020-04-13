package frog.learn.spring.bucks.controller;

import frog.learn.spring.bucks.controller.request.NewOrderRequest;
import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.jpademo.model.CoffeeOrder;
import frog.learn.spring.jpademo.service.CoffeeOrderService;
import frog.learn.spring.jpademo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeService coffeeService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder){
        log.info("recive new order {}", newOrder);
        List<Coffee> coffees = coffeeService.getCoffeeByName(newOrder.getItems());
        return coffeeOrderService.createOrder(newOrder.getCustomer(), coffees.toArray(new Coffee[0]));
    }

}
