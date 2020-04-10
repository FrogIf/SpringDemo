package frog.learn.spring.redis.repository;

import frog.learn.spring.redis.model.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, Long> {

    Optional<CoffeeCache> findOneByName(String name);

}
