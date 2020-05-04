package sch.frog.learn.spring.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)   // runner运行优先级
public class AApplicationRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AApplicationRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("A");
    }
}
