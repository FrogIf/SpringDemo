package frog.learn.spring.reactor;

import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.jpademo.model.CoffeeOrder;
import frog.learn.spring.jpademo.model.OrderState;
import frog.learn.spring.reactor.service.R2DBCCoffeeService;
import frog.learn.spring.reactor.service.R2DBCOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
                            CoffeeOrder order = createOrder("xiaoming", c);
                            return r2DBCOrderService.create(order);
                        })
                        .doOnError(t -> log.error("error", t))
                ).subscribe(o -> log.info("create order : {}", o));
    }

    private CoffeeOrder createOrder(String customer, Coffee ... coffees){
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(Arrays.asList(coffees))
                .state(OrderState.INIT).build();
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        return order;
    }
}
