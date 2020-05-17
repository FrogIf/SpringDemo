package sch.frog.learn.spring.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import sch.frog.learn.spring.common.mq.MessageQueue;

/**
 * 咖啡师
 */
public interface Barista {

    // finish order 消费者
    @Input(MessageQueue.FINISH_ORDERS)
    SubscribableChannel finishOrder();

    // new order 生产者
    @Output(MessageQueue.NEW_ORDERS)
    MessageChannel newOrder();
}
