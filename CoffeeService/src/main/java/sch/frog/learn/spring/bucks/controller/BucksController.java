package sch.frog.learn.spring.bucks.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.entity.OrderState;
import sch.frog.learn.spring.jpademo.service.CoffeeOrderService;
import sch.frog.learn.spring.jpademo.service.CoffeeService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequestMapping("bucks")
@RestController
@Slf4j
public class BucksController {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @RequestMapping("test")
    public List<CoffeeOrder> test(String name/*咖啡名称*/, String customer/*顾客名称*/){
        Optional<Coffee> optional = coffeeService.findOneCoffee(name);

        if(optional.isPresent()){
            CoffeeOrder order = coffeeOrderService.createOrder(customer, Collections.singletonList(optional.get()));
            boolean success = coffeeOrderService.updateState(order, OrderState.PAID);
            log.info("success : {}", success);
            success = coffeeOrderService.updateState(order, OrderState.INIT);
            log.info("success : {}", success);
        }

        return coffeeOrderService.findAllByCustomer(customer);
    }

    @RequestMapping("cachetest")
    public List<Coffee> cacheTest(){
        log.info("size : {}", coffeeService.findAll().size());
        for (int i = 0; i < 10; i++){
            log.info("load from cache.");
            coffeeService.findAll();
        }

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        log.info("5 seconds, auto invalid cache.");

        return coffeeService.findAll();
    }

}
