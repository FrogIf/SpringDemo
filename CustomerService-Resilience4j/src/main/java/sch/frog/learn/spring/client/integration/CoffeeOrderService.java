package sch.frog.learn.spring.client.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.web.request.NewOrderRequest;
import sch.frog.learn.spring.common.web.request.OrderStateRequest;

@FeignClient(name = "frog-coffee", contextId = "coffeeOrder", path = "/order")
public interface CoffeeOrderService {

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CoffeeOrder create(@RequestBody NewOrderRequest orderRequest);

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CoffeeOrder getOrder(@PathVariable("id") Long id);

    @PutMapping(path = "/{id}/{starter}")
    boolean updateState(@PathVariable("id") Long id, @PathVariable("starter") String starter, @RequestBody OrderStateRequest orderState);
}
