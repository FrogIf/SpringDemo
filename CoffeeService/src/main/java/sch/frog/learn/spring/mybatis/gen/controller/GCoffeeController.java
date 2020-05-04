package sch.frog.learn.spring.mybatis.gen.controller;

import sch.frog.learn.spring.mybatis.gen.service.GCoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("mgen")
@RestController
public class GCoffeeController {

    @Autowired
    private GCoffeeService gCoffeeService;

    @RequestMapping("gendemo")
    public String gendemo(){
        gCoffeeService.demo();
        return "success";
    }

}
