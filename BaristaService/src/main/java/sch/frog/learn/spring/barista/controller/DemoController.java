package sch.frog.learn.spring.barista.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("touch")
    public String demo(){
        return "touch";
    }

}
