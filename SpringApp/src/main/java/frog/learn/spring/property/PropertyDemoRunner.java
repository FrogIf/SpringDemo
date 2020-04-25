package frog.learn.spring.property;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PropertyDemoRunner implements ApplicationRunner {

    @Value("${frog.hello}")
    private String frogHello;

    @Value("${frog.sch.A}")
    private String frogA;

    @Value("${frog.sch.B}")
    private String frogB;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(">>>> custom property frogHello : {}", frogHello);
        log.info(">>>> custom property frogA : {}", frogA);
        log.info(">>>> custom property frogB : {}", frogB);
    }
}
