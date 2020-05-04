package sch.frog.learn.spring.redis.service;

import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.jpademo.dao.CoffeeRepository;
import sch.frog.learn.spring.redis.model.CoffeeCache;
import sch.frog.learn.spring.redis.repository.CoffeeCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CoffeeCacheService {

    @Autowired
    private CoffeeCacheRepository coffeeCacheRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Optional<Coffee> findSimpleCoffeeFromCache(String name){
        Optional<CoffeeCache> cached = coffeeCacheRepository.findOneByName(name);
        if(cached.isPresent()){
            CoffeeCache coffeeCache = cached.get();
            Coffee coffee = Coffee.builder()
                    .name(coffeeCache.getName())
                    .price(coffeeCache.getPrice())
                    .build();
            log.info("Coffee {} found in cache.", coffeeCache);
            return Optional.of(coffee);
        }else{
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());
            Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
            coffee.ifPresent(c -> {
                CoffeeCache coffeeCache = CoffeeCache.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .price(c.getPrice())
                        .build();
                log.info("save coffee {} to cache.", coffeeCache);
                coffeeCacheRepository.save(coffeeCache);
            });
            return coffee;
        }
    }

}
