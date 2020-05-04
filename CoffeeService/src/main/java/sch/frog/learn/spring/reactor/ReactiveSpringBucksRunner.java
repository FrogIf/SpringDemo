package sch.frog.learn.spring.reactor;

import sch.frog.learn.spring.common.entity.OrderState;
import sch.frog.learn.spring.reactor.model.RCoffee;
import sch.frog.learn.spring.reactor.model.RCoffeeOrder;
import sch.frog.learn.spring.reactor.service.R2DBCCoffeeService;
import sch.frog.learn.spring.reactor.service.R2DBCOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Arrays;
import java.util.Date;

//@Component
@Slf4j
public class ReactiveSpringBucksRunner implements ApplicationRunner {
    @Autowired
    private R2DBCCoffeeService r2DBCCoffeeService;

    @Autowired
    private R2DBCOrderService r2DBCOrderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("reactive springbucks run");
        r2DBCCoffeeService.initCache()
                .then(
                        r2DBCCoffeeService.findOneCoffee("latte")
                        .flatMap(c -> {
                            RCoffeeOrder order = createOrder("xiaoming", c);
                            return r2DBCOrderService.create(order);
                        })
                        .doOnError(t -> log.error("error", t))
                ).subscribe(o -> log.info("create order : {}", o));
    }

    private RCoffeeOrder createOrder(String customer, RCoffee... coffees){
        RCoffeeOrder order = RCoffeeOrder.builder()
                .customer(customer)
                .items(Arrays.asList(coffees))
                .state(OrderState.INIT).build();
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        return order;
    }
}
