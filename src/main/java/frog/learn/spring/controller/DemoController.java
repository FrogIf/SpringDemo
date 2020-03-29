package frog.learn.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class DemoController {

    @RequestMapping("helloWorld")
    public String helloWorld(){
        return "hello world!";
    }

}
