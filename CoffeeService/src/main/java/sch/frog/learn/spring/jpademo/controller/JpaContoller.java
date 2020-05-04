package sch.frog.learn.spring.jpademo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sch.frog.learn.spring.jpademo.service.CoffeeOrderService;

@RequestMapping("jpa")
@RestController
public class JpaContoller {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @RequestMapping("orderdemo")
    public String jpaDemo(){
        coffeeOrderService.initOrders();
        coffeeOrderService.findOrders();
        return "success";
    }


}
