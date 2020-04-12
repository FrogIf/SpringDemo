package frog.learn.spring.reactor.repository;

import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.reactor.anno.R2DBCRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

@R2DBCRepository
public interface R2DBCCoffeeRepository extends ReactiveCrudRepository<Coffee, Long> {

    @Query("select * from t_coffee where name = $1")
    Mono<Coffee> findByName(String name);

}
