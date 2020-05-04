package sch.frog.learn.spring.reactor.repository;

import sch.frog.learn.spring.common.entity.OrderState;
import sch.frog.learn.spring.reactor.anno.R2DBCRepository;
import sch.frog.learn.spring.reactor.model.RCoffee;
import sch.frog.learn.spring.reactor.model.RCoffeeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

@R2DBCRepository
@Repository
public class R2DBCCoffeeOrderRepository {

    @Autowired
    private DatabaseClient databaseClient;

    public Mono<RCoffeeOrder> get(Long id){
        return databaseClient.execute()
                .sql("select * from t_order where id = " + id)
                .map((row, rowmeta) -> {
                    RCoffeeOrder order = RCoffeeOrder.builder()
                            .state(OrderState.values()[row.get("state", Integer.class)])
                            .items(new ArrayList<>())
                            .build();
                    order.setCustomer(row.get("customer", String.class));
                    order.setId(id);
                    order.setCreateTime(row.get("create_time", Date.class));
                    order.setUpdateTime(row.get("update_time", Date.class));
                    return order;
                })
                .first()
                .flatMap(o ->
                        databaseClient.execute().sql("select * from t_coffee c, t_order_coffee oc "
                        + "where c.id = oc.items_id and oc.coffee_order_id = " + id)
                        .as(RCoffee.class)
                        .fetch()
                        .all()
                        .collectList()
                        .flatMap(l -> {
                           o.getItems().addAll(l);
                           return Mono.just(o);
                        })
                );
    }

    public Mono<Long> save(RCoffeeOrder order){
        if(order.getCreateTime() == null){
            order.setCreateTime(new Date());
        }
        if(order.getUpdateTime() == null){
            order.setUpdateTime(new Date());
        }
        return databaseClient.insert().into("t_order")
                .value("customer", order.getCustomer())
                .value("state", order.getState().ordinal())
                .value("create_time", new Timestamp(order.getCreateTime().getTime()))
                .value("update_time", new Timestamp(order.getUpdateTime().getTime()))
                .fetch()
                .first()
                .flatMap(m -> Mono.just((Long) m.get("ID")))
                .flatMap(id -> Flux.fromIterable(order.getItems())
                        .flatMap(c -> databaseClient.insert().into("t_order_coffee")
                                .value("coffee_order_id", id)
                                .value("items_id", c.getId())
                                .then())
                        .then(Mono.just(id))
                );
    }

}
