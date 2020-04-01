package frog.learn.spring.jpademo.controller;

import frog.learn.spring.jpademo.service.CoffeeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("jpademo")
@RestController
public class JpaContoller {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @RequestMapping("orderdemo")
    public String jpaDemo(){
        coffeeOrderService.initOrders();
        return "success";
    }


}
