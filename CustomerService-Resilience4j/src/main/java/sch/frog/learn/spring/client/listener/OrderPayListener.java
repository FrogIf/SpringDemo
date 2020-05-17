package sch.frog.learn.spring.client.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sch.frog.learn.spring.client.scheduler.CoffeeOrderScheduler;
import sch.frog.learn.spring.client.support.OrderWaitingEvent;

//@Component
public class OrderPayListener {

    @Autowired
    private CoffeeOrderScheduler coffeeOrderScheduler;

    @EventListener
    public void acceptOrder(OrderWaitingEvent event){
        coffeeOrderScheduler.addMonitorOrder(event.getOrder());
    }
}
