package frog.learn.spring.mybatisdemo.controller;

import frog.learn.spring.mybatisdemo.model.MCoffee;
import frog.learn.spring.mybatisdemo.service.MCoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("mybatis")
@RestController
@Slf4j
public class MaybatisDemoController {

    @Autowired
    private MCoffeeService mCoffeeService;


    @RequestMapping("demo")
    public String demo(){
        MCoffee mCoffee = MCoffee.builder().name("latte").price(Money.of(CurrencyUnit.of("CNY"), 10)).build();
        mCoffeeService.save(mCoffee);
        log.info("coffee : {}", mCoffee);
        MCoffee qCoffee = mCoffeeService.queryById(mCoffee.getId());
        log.info("coffee : {}", qCoffee);
        return "success";
    }

}
