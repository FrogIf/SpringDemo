package frog.learn.spring.reactor.service.impl;

import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.reactor.repository.R2DBCCoffeeRepository;
import frog.learn.spring.reactor.service.R2DBCCoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class R2DBCCoffeeServiceImpl implements R2DBCCoffeeService {

    private static final String PREFIX = "springbucks-";

    @Autowired
    private R2DBCCoffeeRepository r2DBCCoffeeRepository;

    @Autowired
    private ReactiveRedisTemplate<String, Coffee> reactiveRedisTemplate;

    @Override
    public Flux<Boolean> initCache(){
        return r2DBCCoffeeRepository.findAll()
                .flatMap(c -> reactiveRedisTemplate.opsForValue()
                        .set(PREFIX + c.getName(), c)   // 这里放入c之后, 由于R2DBCConfigure中的配置, 使用自定义的序列化
                        .flatMap(b -> reactiveRedisTemplate.expire(PREFIX + c.getName(), Duration.ofMinutes(1)))
                        .doOnSuccess(v -> log.info("Loading and caching {}", c))
                );
    }

    @Override
    public Mono<Coffee> findOneCoffee(String name){
        return reactiveRedisTemplate.opsForValue().get(PREFIX + name)
                .switchIfEmpty(r2DBCCoffeeRepository.findByName(name)
                        .doOnSuccess(s -> log.info("Loading {} from DB.", name))
                );
    }

}
