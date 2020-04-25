package sch.frog.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
public class FrogDotRunner implements ApplicationRunner {

    public FrogDotRunner() {
        log.info("----------------Initializer FrogDotRunner--------------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("FROG DOT.....................");
    }

}
