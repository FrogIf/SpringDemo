package frog.learn.spring.contextdemo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestBean {

    private String name;

    public TestBean(String name) {
        this.name = name;
    }

    public void hello(){
        log.info("hello : {}", name);
    }
}
