package sch.frog.learn.spring;

import org.springframework.cloud.stream.annotation.EnableBinding;
import sch.frog.learn.spring.mq.Barista;
import sch.frog.learn.spring.mq.Customer;

@EnableBinding({Barista.class, Customer.class})
public class MessageConfigure {
}
