package sch.frog.learn.spring.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import sch.frog.learn.spring.common.mq.MessageQueue;

public interface Customer {
    @Output(MessageQueue.NOTIFY_ORDERS)
    MessageChannel notification();
}
