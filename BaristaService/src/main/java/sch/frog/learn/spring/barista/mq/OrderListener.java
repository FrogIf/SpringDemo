package sch.frog.learn.spring.barista.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sch.frog.learn.spring.barista.repository.CoffeeOrderRepository;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.entity.OrderState;
import sch.frog.learn.spring.common.mq.MessageQueue;

@Component
@Slf4j
@Transactional
public class OrderListener {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    @Autowired
    @Qualifier(MessageQueue.FINISH_ORDERS)
    private MessageChannel finishedOrdersMessageChannel;


    @Value("${order.barista-prefix}${random.uuid}")
    private String barista;

    @StreamListener(MessageQueue.NEW_ORDERS)
    public void processNewOrder(Long id){
        CoffeeOrder order = coffeeOrderRepository.getOne(id);
        if(order == null){
            log.warn("Order {} is not valid.", id);
            return;
        }
        log.info("Receive a new Order {}. waiter : {}, Customer: {}", id, order.getWaiter(), order.getCustomer());
        order.setState(OrderState.BREWED);
        order.setBarista(barista);
        coffeeOrderRepository.save(order);
        log.info("Order {} is ready.", id);
        finishedOrdersMessageChannel.send(MessageBuilder.withPayload(id).build());
    }


}
