package sch.frog.learn.spring.client.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sch.frog.learn.spring.client.integration.CoffeeOrderService;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.entity.OrderState;
import sch.frog.learn.spring.common.web.request.OrderStateRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Component
@Slf4j
public class CoffeeOrderScheduler {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    private final Map<Long, CoffeeOrder> orderMap = new ConcurrentHashMap<>();

    public void addMonitorOrder(CoffeeOrder coffeeOrder){
        orderMap.put(coffeeOrder.getId(), coffeeOrder);
    }

    @Scheduled(fixedRate = 1000)    // 每一秒查询一次
    public void waitForCoffeeFinish(){
        if(orderMap.isEmpty()){
            return;
        }
        log.info("I'm waiting for my coffee.");
        orderMap.values().stream()
                .map(o -> coffeeOrderService.getOrder(o.getId()))
                .filter(o -> OrderState.BREWED == o.getState())
                .forEach(o -> {
                    log.info("Order [{}] is finish, I'll take it.", o.getId());
                    coffeeOrderService.updateState(o.getId(), "0", new OrderStateRequest(OrderState.TAKEN));
                    orderMap.remove(o.getId());
                });
    }

}
