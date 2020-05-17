package sch.frog.learn.spring.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import sch.frog.learn.spring.common.mq.MessageQueue;

@Component
@Slf4j
public class OrderListener {

    @StreamListener(MessageQueue.FINISH_ORDERS)
    public void listenOrderFinish(Long id){
        log.info("we've finished an order: [{}]", id);
    }

}
