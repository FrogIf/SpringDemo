package sch.frog.learn.spring;

import org.springframework.cloud.stream.annotation.EnableBinding;
import sch.frog.learn.spring.mq.Barista;

@EnableBinding(Barista.class)
public class MessageConfigure {
}
