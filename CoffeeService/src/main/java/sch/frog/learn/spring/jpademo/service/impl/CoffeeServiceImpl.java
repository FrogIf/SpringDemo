package sch.frog.learn.spring.jpademo.service.impl;

import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.jpademo.dao.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sch.frog.learn.spring.jpademo.service.CoffeeService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@CacheConfig(cacheNames = "coffee")
public class CoffeeServiceImpl implements CoffeeService {

    private static final String CACHE = "springbucks-coffee";

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    @Override
    public Optional<Coffee> findOneCoffee(String name){
        HashOperations<String, String, Coffee> hashOperations = redisTemplate.opsForHash();
        if(redisTemplate.hasKey(CACHE) && hashOperations.hasKey(CACHE, name)){
            log.info("GET coffee {} from redis.", name);
            return Optional.of(hashOperations.get(CACHE, name));
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found : {}", coffee);

        if(coffee.isPresent()){
            log.info("put coffee {} to redis.", name);
            hashOperations.put(CACHE, name, coffee.get());
            redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES);   // 一定要设置过期时间
        }
        return coffee;
    }

    @Override
    @Cacheable
    public List<Coffee> findAll() {
        return coffeeRepository.findAll();
    }

    @Override
    @CacheEvict
    public void reloadCoffee() {

    }

    @Override
    public List<Coffee> getCoffeeByName(List<String> names) {
        return coffeeRepository.findByNameInOrderById(names);
    }

    @Override
    public Coffee getCoffee(Long id) {
        return coffeeRepository.getOne(id);
    }

    @Override
    public Coffee saveCoffee(String name, Money price) {
        Coffee coffee = Coffee.builder()
                .name(name)
                .price(price)
                .build();
        return coffeeRepository.save(coffee);
    }

    @Override
    public long getCoffeeCount() {
        return coffeeRepository.count();
    }

}
