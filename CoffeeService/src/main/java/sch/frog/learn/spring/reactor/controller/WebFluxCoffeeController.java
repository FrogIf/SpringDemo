package sch.frog.learn.spring.reactor.controller;

import sch.frog.learn.spring.reactor.model.RCoffee;
import sch.frog.learn.spring.reactor.repository.R2DBCCoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/wcoffee")
@Slf4j
public class WebFluxCoffeeController {

    @Autowired
    private R2DBCCoffeeRepository coffeeRepository;

    @GetMapping(path = "/", params = "!name")
    @ResponseBody
    public Flux<RCoffee> getAll(){
        log.info("web flux query all.");
        return coffeeRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public Mono<RCoffee> getById(@PathVariable Long id){
        log.info("web flux query by id");
        return coffeeRepository.findById(id);
    }

    @GetMapping(path = "/", params = "name")
    @ResponseBody
    public Mono<RCoffee> getByName(String name){
        log.info("web flux query by name");
        return coffeeRepository.findByName(name);
    }


}
