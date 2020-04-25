package frog.learn.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sch.frog.ball.FrogBallRunner;

@Configuration
public class CustomConfig {

    @Bean
    public FrogBallRunner frogBallRunner(){
        return new FrogBallRunner("kkk");
    }
}
