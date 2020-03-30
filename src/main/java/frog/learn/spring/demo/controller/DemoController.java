package frog.learn.spring.demo.controller;

import frog.learn.spring.demo.service.JdbcDemoService;
import frog.learn.spring.demo.service.TransactionDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class DemoController {

    @RequestMapping("helloWorld")
    public String helloWorld(){
        return "hello world!";
    }

    @Autowired
    private JdbcDemoService jdbcDemoService;

    @Autowired
    private TransactionDemoService transactionDemoService;

    @RequestMapping("testJdbc")
    public String testJdbc(){
        jdbcDemoService.insertDataDemo();
        jdbcDemoService.batchInsertDemo();
        jdbcDemoService.queryDataDemo();
        return "jdbc test";
    }

    @RequestMapping("testTransaction/{type}")
    public String testTransaction(@PathVariable("type") String type){
        if("program".equals(type)){
            transactionDemoService.programmaticTransactionDemo();
        }else{
            transactionDemoService.declarativeTransactionDemo();
        }
        return "transaction test";
    }

}
