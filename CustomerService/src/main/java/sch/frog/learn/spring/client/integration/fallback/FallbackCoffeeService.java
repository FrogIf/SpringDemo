package sch.frog.learn.spring.client.integration.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sch.frog.learn.spring.client.integration.CoffeeService;
import sch.frog.learn.spring.common.entity.Coffee;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class FallbackCoffeeService implements CoffeeService {
    @Override
    public List<Coffee> findAll() {
        log.warn("fallback to empty list.");
        return Collections.emptyList();
    }
}
