package frog.learn.spring.bucks.controller;

import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.jpademo.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/coffee")
@Controller
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/")
    @ResponseBody
    public List<Coffee> getAll(){
        return coffeeService.findAll();
    }

}
