package sch.frog.learn.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CustomerServiceResilienceApp {

    public static void main(String[] args){
        SpringApplication.run(CustomerServiceResilienceApp.class, args);
    }
}
