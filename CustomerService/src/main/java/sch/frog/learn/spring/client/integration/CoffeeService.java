package sch.frog.learn.spring.client.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sch.frog.learn.spring.client.integration.fallback.FallbackCoffeeService;
import sch.frog.learn.spring.common.entity.Coffee;

import java.util.List;

@FeignClient(name = "frog-coffee", contextId = "coffee", path = "/coffee", qualifier = "coffeeService", fallback = FallbackCoffeeService.class)
public interface CoffeeService {
    @RequestMapping(path = "/", params = "!name", method = RequestMethod.GET)
    List<Coffee> findAll();

    @RequestMapping(path = "/touch")
    String touch();
}
