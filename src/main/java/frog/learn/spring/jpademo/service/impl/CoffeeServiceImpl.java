package frog.learn.spring.jpademo.service.impl;

import frog.learn.spring.jpademo.dao.CoffeeRepository;
import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.jpademo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@CacheConfig(cacheNames = "coffee")
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public Optional<Coffee> findOneCoffee(String name){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found : {}", coffee);
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

}
