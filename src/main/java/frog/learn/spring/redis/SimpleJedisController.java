package frog.learn.spring.redis;

import frog.learn.spring.constant.CommonConstant;
import frog.learn.spring.jpademo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

@Configuration
@RequestMapping("jedis")
@RestController
@Slf4j
public class SimpleJedisController {

    // ----------配置项-----------
    @Bean
    @ConfigurationProperties("redis")
    public JedisPoolConfig jedisPoolConfig(){
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${redis.host}") String host){
        return new JedisPool(jedisPoolConfig(), host);
    }

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    @Autowired
    private CoffeeService coffeeService;

    @RequestMapping("demo")
    public String demo(){
        log.info(jedisPoolConfig.toString());

        try(
                Jedis jedis = jedisPool.getResource();
                ){
            coffeeService.findAll().forEach(coffee -> {
                jedis.hset("springbucks-menu", coffee.getName(), Long.toString(coffee.getPrice().getAmountMinorLong()));
            });

            Map<String, String> menu = jedis.hgetAll("springbucks-menu");
            log.info("menu : {}", menu);

            String price = jedis.hget("springbucks-menu", "espresso");
            log.info("price : {}", Money.ofMinor(CurrencyUnit.of(CommonConstant.MONEY_UNIT), Long.parseLong(price)));
        }

        return "successs";
    }



}
