package sch.frog.learn.spring.redis.repository;

import sch.frog.learn.spring.redis.anno.CacheRepository;
import sch.frog.learn.spring.redis.model.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@CacheRepository    // 标注其为cacheRepository, 使其不被data jpa扫描到
public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, Long> {

    Optional<CoffeeCache> findOneByName(String name);

}
