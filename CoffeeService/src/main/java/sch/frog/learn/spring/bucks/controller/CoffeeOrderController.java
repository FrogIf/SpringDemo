package sch.frog.learn.spring.bucks.controller;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.entity.OrderState;
import sch.frog.learn.spring.common.web.request.NewOrderRequest;
import sch.frog.learn.spring.common.web.request.OrderStateRequest;
import sch.frog.learn.spring.jpademo.service.CoffeeOrderService;
import sch.frog.learn.spring.jpademo.service.CoffeeService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {

    private RateLimiter rateLimiter;

    public CoffeeOrderController(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiter = rateLimiterRegistry.rateLimiter("order");
    }

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeService coffeeService;

    @io.github.resilience4j.ratelimiter.annotation.RateLimiter(name = "order")
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder){
        log.info("recive new order {}", newOrder);
        List<Coffee> coffees = coffeeService.getCoffeeByName(newOrder.getItems());
        return coffeeOrderService.createOrder(newOrder.getCustomer(), coffees);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CoffeeOrder getOrder(@PathVariable("id") Long id){
        CoffeeOrder order = null;
        try{
            order = coffeeOrderService.get(id);
            log.info("Get Order : {}", order);
        }catch (RequestNotPermitted e){
            log.warn("Request Not Permitted! {}", e.getMessage());
        }

        return order;
    }

    @ModelAttribute
    public List<Coffee> coffeeList(){
        return coffeeService.findAll();
    }

    @GetMapping(path = "/")
    public ModelAndView showCreateForm(){
        return new ModelAndView("create-order-form");
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createOrder(@Valid NewOrderRequest order, BindingResult result, ModelMap map){
        if(result.hasErrors()){
            log.warn("binding result : {}", result);
            map.addAttribute("message", result.toString());
            return "create-order-form";
        }

        List<Coffee> coffees = coffeeService.getCoffeeByName(order.getItems());
        CoffeeOrder newOrder = coffeeOrderService.createOrder(order.getCustomer(), coffees);
        return "redirect:/order/" + newOrder.getId();
    }

    @PutMapping(path = "/{id}/{starter}")
    @ResponseBody
    public boolean updateState(@PathVariable("id") Long id, @PathVariable("starter") String starter, @RequestBody OrderStateRequest orderState){
        return rateLimiter.executeSupplier(() -> {  // 限流
            CoffeeOrder coffeeOrder = coffeeOrderService.get(id);
            log.info("pay : {}", coffeeOrder);
            return rateLimiter.executeSupplier(() -> coffeeOrderService.updateState(coffeeOrder, orderState.getOrderState(), starter));  // 限流
        });
    }
}
