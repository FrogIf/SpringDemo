package sch.frog.learn.spring.reactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class RedisReactorDemo  {

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    public void run() {
        ReactiveHashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String key = "reactive_test";

        CountDownLatch cdl = new CountDownLatch(1);

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 14);
        map.put("b", 16);
        map.put("c", 77);
        map.put("d", 23);
        map.put("e", 88);

        Flux.fromIterable(map.entrySet())
                .publishOn(Schedulers.single())
                .doOnComplete(() -> log.info("list ok."))
                .flatMap(c -> {
                    log.info("try to put {}, {}", c.getKey(), c.getValue());
                    return hashOperations.put(key, c.getKey(), Integer.toString(c.getValue()));
                })
                .doOnComplete(() -> log.info("set ok."))
                .concatWith(redisTemplate.expire(key, Duration.ofMinutes(1)))
                .doOnComplete(() -> log.info("expire ok."))
                .onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(false);
                })
                .subscribe(b -> log.info("result : {}", b),
                        e -> log.error(e.getMessage(), e),
                        cdl::countDown
                );

        log.info("waiting");
        try {
            cdl.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
