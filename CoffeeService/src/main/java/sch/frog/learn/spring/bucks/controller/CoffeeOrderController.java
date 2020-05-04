package sch.frog.learn.spring.bucks.controller;

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
import sch.frog.learn.spring.common.web.request.NewOrderRequest;
import sch.frog.learn.spring.jpademo.service.CoffeeOrderService;
import sch.frog.learn.spring.jpademo.service.CoffeeService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeService coffeeService;

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
        return coffeeOrderService.get(id);
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
}
