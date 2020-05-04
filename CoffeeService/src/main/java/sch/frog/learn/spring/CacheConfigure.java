package sch.frog.learn.spring;

import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.redis.anno.CacheRepository;
import sch.frog.learn.spring.redis.converter.BytesToMoneyConverter;
import sch.frog.learn.spring.redis.converter.MoneyToBytesConverter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Arrays;

@Configuration
@EnableCaching(proxyTargetClass = true)
@EnableRedisRepositories(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = CacheRepository.class)
)
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

}
