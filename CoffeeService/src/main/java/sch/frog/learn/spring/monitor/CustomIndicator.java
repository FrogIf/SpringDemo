package sch.frog.learn.spring.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import sch.frog.learn.spring.jpademo.service.CoffeeService;

/**
 * 自定义Actuator的health监控器
 */
@Component
public class CustomIndicator implements HealthIndicator {

    @Autowired
    private CoffeeService coffeeService;

    @Override
    public Health health() {
        long count = coffeeService.getCoffeeCount();

        Health health;
        if(count > 0){
            health = Health.up()
                    .withDetail("count", count)
                    .withDetail("message", "we have enough coffee.")
                    .build();
        }else{
            health = Health.down()
                    .withDetail("count", 0)
                    .withDetail("message", "we are out of coffee.")
                    .build();
        }

        return health;
    }

}
