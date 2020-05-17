package sch.frog.learn.spring.client.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import sch.frog.learn.spring.client.integration.CoffeeOrderService;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.entity.OrderState;
import sch.frog.learn.spring.common.mq.MessageQueue;
import sch.frog.learn.spring.common.web.request.OrderStateRequest;

@Component
@Slf4j
public class NotificationListener {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Value("${customer.mark}")
    private String customer;

    @StreamListener(MessageQueue.NOTIFY_ORDERS)
    public void takeOrder(@Payload Long id){
        CoffeeOrder order = coffeeOrderService.getOrder(id);
        if(OrderState.BREWED == order.getState()){
            log.info("Order {} is ready. I'll take it.", id);
            coffeeOrderService.updateState(id, "0", new OrderStateRequest(OrderState.TAKEN));
        }else{
            log.warn("Order {} is not ready. why are you notify me?", id);
        }
    }
}
