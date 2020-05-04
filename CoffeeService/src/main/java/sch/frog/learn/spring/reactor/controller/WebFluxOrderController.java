package sch.frog.learn.spring.reactor.controller;

import sch.frog.learn.spring.common.entity.OrderState;
import sch.frog.learn.spring.common.web.request.NewOrderRequest;
import sch.frog.learn.spring.reactor.model.RCoffeeOrder;
import sch.frog.learn.spring.reactor.repository.R2DBCCoffeeOrderRepository;
import sch.frog.learn.spring.reactor.repository.R2DBCCoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("worder")
@Slf4j
public class WebFluxOrderController {

    @Autowired
    private R2DBCCoffeeOrderRepository orderRepository;

    @Autowired
    private R2DBCCoffeeRepository coffeeRepository;

    @GetMapping("/{id}")
    @ResponseBody
    public Mono<RCoffeeOrder> getById(@PathVariable  Long id){
        log.info("web flux query by id.");
        return orderRepository.get(id);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public Mono<RCoffeeOrder> create(@RequestBody NewOrderRequest orderRequest){
        log.info("web flux create order");
        return Flux.fromStream(orderRequest.getItems().stream())
                .flatMap(n -> coffeeRepository.findByName(n))
                .collectList()
                .flatMap(l ->
                    orderRepository.save(
                            RCoffeeOrder.builder()
                                    .customer(orderRequest.getCustomer())
                                    .items(l)
                                    .state(OrderState.INIT)
                                    .build())

                )
                .flatMap(id -> orderRepository.get(id));

    }

}
