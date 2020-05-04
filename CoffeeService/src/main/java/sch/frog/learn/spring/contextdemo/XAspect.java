package sch.frog.learn.spring.contextdemo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class XAspect {

    @AfterReturning("bean(testBean*)")
    public void printAfter(){
        log.info("after hello.");
    }

}
