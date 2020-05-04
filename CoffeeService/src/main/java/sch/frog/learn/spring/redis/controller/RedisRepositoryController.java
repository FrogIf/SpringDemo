package sch.frog.learn.spring.redis.controller;

import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.redis.service.CoffeeCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("redis")
public class RedisRepositoryController {

    @Autowired
    private CoffeeCacheService coffeeCacheService;

    @RequestMapping("repository")
    public Coffee findCoffee(String name){
        Optional<Coffee> coffee = coffeeCacheService.findSimpleCoffeeFromCache(name);
        return coffee.orElse(null);
    }

}
