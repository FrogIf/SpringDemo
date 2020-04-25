package frog.learn.spring.contextdemo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestBean {

    private String name;

    public TestBean(String name) {
        this.name = name;
        log.info("initializer test bean : {}", name);
    }

    public void hello(){
        log.info("hello : {}", name);
    }
}
