package sch.frog.custom.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sch.frog.ball.FrogBallRunner;

@Configuration
@ConditionalOnClass(FrogBallRunner.class)
public class FrogCustomConfiguration {

    /*
     * 当frog.enable = true时, 才会进行配置
     * matchIfMissing = true, 当frog.enable没有配置的时候, 也会启动这个配置
     */

    @Bean
    @ConditionalOnMissingBean(FrogBallRunner.class)
    @ConditionalOnProperty(name = "frog.enabled", havingValue = "true", matchIfMissing = true)
    public FrogBallRunner frogBallRunner(){
        return new FrogBallRunner();
    }

}
