package sch.frog.learn.spring.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "frog.discovery.enable", havingValue = "true", matchIfMissing = true)
public class FrogDiscoveryAutoConfig {

    @Bean
    public DiscoveryClient frogDiscoveryClient(){
        return new FrogDiscoveryClient();
    }

}
