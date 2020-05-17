package sch.frog.learn.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import sch.frog.learn.spring.client.integration.Waiter;

@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableAspectJAutoProxy
@EnableBinding(Waiter.class)
public class CustomerServiceResilienceApp {

    public static void main(String[] args){
        SpringApplication.run(CustomerServiceResilienceApp.class, args);
    }
}
