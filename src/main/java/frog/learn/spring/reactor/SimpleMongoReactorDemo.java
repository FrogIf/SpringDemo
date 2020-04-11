package frog.learn.spring.reactor;

import frog.learn.spring.mongo.model.MongoCoffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SimpleMongoReactorDemo {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;    // 不需要配置, 直接注入即可

    public void testDemo(){
        this.startFromInsertion(this::decreaseHighPrice);
    }

    private void startFromInsertion(Runnable runnable){
        mongoTemplate.insertAll(initCoffee())
                .publishOn(Schedulers.elastic())
                .doOnNext(c -> log.info("Next : {}", c))
                .doOnComplete(runnable)
                .doFinally(s -> {
                    log.info("Finally 1, {}", s);
                })
                .count()
                .subscribe(c -> log.info("Insert {} records", c));
    }

    private void decreaseHighPrice(){
        mongoTemplate.updateMulti(Query.query(Criteria.where("price").gte(3000L)), new Update().inc("price", -500L).currentDate("updateTime"), MongoCoffee.class)
                .doFinally(s -> {
                    log.info("Finally 2, {}", s);
                })
                .subscribe(r -> log.info("Result is {}", r));
    }


    private List<MongoCoffee> initCoffee(){
        MongoCoffee espresso = MongoCoffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        MongoCoffee latte = MongoCoffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        return Arrays.asList(espresso, latte);
    }


}
