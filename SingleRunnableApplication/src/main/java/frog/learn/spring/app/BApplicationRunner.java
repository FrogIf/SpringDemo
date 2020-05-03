package frog.learn.spring.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class BApplicationRunner implements ApplicationRunner {
    private final static Logger log = LoggerFactory.getLogger(BApplicationRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("B");
    }
}
