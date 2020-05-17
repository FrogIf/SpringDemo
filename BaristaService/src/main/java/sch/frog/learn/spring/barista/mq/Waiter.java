package sch.frog.learn.spring.barista.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import sch.frog.learn.spring.common.mq.MessageQueue;

public interface Waiter {
    @Input(MessageQueue.NEW_ORDERS)
    SubscribableChannel newOrder();

    @Output(MessageQueue.FINISH_ORDERS)
    MessageChannel finishedOrder();
}
