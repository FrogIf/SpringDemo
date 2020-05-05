package sch.frog.learn.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;

@SpringBootApplication(exclude = {SimpleDiscoveryClientAutoConfiguration.class, CompositeDiscoveryClientAutoConfiguration.class})
@Slf4j
public class CustomerServiceApp {

    public static void main(String[] args){
        SpringApplication.run(CustomerServiceApp.class, args);
    }
}
