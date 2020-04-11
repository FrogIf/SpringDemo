package frog.learn.spring;

import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.redis.converter.BytesToMoneyConverter;
import frog.learn.spring.redis.converter.MoneyToBytesConverter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Arrays;

@Configuration
@EnableCaching(proxyTargetClass = true)
@EnableRedisRepositories
public class CacheConfigure {

    /**
     * 注册一个特定对象的redisTemplate
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Coffee> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Coffee> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisCustomConversions redisCustomConversions(){
        return new RedisCustomConversions(Arrays.asList(new MoneyToBytesConverter(), new BytesToMoneyConverter()));
    }

    @Bean
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(ReactiveRedisConnectionFactory connectionFactory){
        return new ReactiveStringRedisTemplate(connectionFactory);
    }

}
