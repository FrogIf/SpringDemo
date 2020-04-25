package sch.frog.ball;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
public class FrogBallRunner implements ApplicationRunner {

    public FrogBallRunner() {
        log.info("----------------Initializing FrogBallRunner. Frog-----------------");
    }

    public FrogBallRunner(String name){
        log.info("----------------Initializing FrogBallRunner. {}-----------------", name);
    }

    public void run(ApplicationArguments args) throws Exception {
        log.info("Hello Spring!");
    }
}
