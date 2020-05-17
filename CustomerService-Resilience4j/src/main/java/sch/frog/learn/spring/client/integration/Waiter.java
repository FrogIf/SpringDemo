package sch.frog.learn.spring.client.integration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import sch.frog.learn.spring.common.mq.MessageQueue;

public interface Waiter {
    @Input(MessageQueue.NOTIFY_ORDERS)
    SubscribableChannel notification();
}
