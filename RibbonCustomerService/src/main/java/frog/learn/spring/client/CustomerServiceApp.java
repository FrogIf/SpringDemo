package frog.learn.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CustomerServiceApp {

    public static void main(String[] args){
        SpringApplication.run(CustomerServiceApp.class, args);
    }
}
