package sch.frog.learn.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sch.frog.learn.spring.barista.mq.Waiter;

@SpringBootApplication
@EnableJpaRepositories
@EnableBinding(Waiter.class)
public class BaristaApp {

    public static void main(String[] args){
        SpringApplication.run(BaristaApp.class, args);
    }

}