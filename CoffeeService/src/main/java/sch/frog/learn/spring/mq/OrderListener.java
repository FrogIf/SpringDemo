package sch.frog.learn.spring.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import sch.frog.learn.spring.common.mq.MessageQueue;
import sch.frog.learn.spring.common.mq.model.OrderMessageBody;

@Component
@Slf4j
public class OrderListener {

    @Autowired
    private Customer customer;

    @StreamListener(MessageQueue.FINISH_ORDERS)
    public void listenOrderFinish(OrderMessageBody orderMessageBody){
        Long id = orderMessageBody.getId();
        log.info("we've finished an order: [{}]", id);
        if(StringUtils.isNotBlank(orderMessageBody.getStarter()) && !"0".equals(orderMessageBody.getStarter())){
            Message<Long> message = MessageBuilder.withPayload(id)
                    .setHeader("starter", orderMessageBody.getStarter())
                    .build();
            customer.notification().send(message);
        }
    }

}
