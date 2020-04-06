package frog.learn.spring.bucks.controller;

import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.jpademo.model.CoffeeOrder;
import frog.learn.spring.jpademo.model.OrderState;
import frog.learn.spring.jpademo.service.CoffeeOrderService;
import frog.learn.spring.jpademo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            CoffeeOrder order = coffeeOrderService.createOrder(customer, optional.get());
            boolean success = coffeeOrderService.updateState(order, OrderState.PAID);
            log.info("success : {}", success);
            success = coffeeOrderService.updateState(order, OrderState.INIT);
            log.info("success : {}", success);
        }

        return coffeeOrderService.findAllByCustomer(customer);
    }

}
