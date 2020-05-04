package sch.frog.learn.spring.reactor.repository;

import sch.frog.learn.spring.reactor.anno.R2DBCRepository;
import sch.frog.learn.spring.reactor.model.RCoffee;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

@R2DBCRepository
public interface R2DBCCoffeeRepository extends ReactiveCrudRepository<RCoffee, Long> {

    @Query("select * from t_coffee where name = $1")
    Mono<RCoffee> findByName(String name);

}
