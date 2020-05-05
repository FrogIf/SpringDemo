package sch.frog.learn.spring.client.breaker;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//@Aspect
//@Component
@Slf4j
public class CircuitBreakerAspect {

    // 阈值
    private static final int THRESHOLD = 3;

    // 用于记录远程调用失败次数
    private Map<String, AtomicInteger> counter = new ConcurrentHashMap<>();

    // 用于记录熔断拦截次数
    private Map<String, AtomicInteger> breakerCounter = new ConcurrentHashMap<>();

    @Around("execution(* sch.frog.learn.spring.client.integration..*(..))")
    public Object doWithCircuitBreaker(ProceedingJoinPoint pjp) throws Throwable{
        String signature = pjp.getSignature().toLongString();
        log.info("Invoke {}", signature);
        Object retVal;

        try{
            if(counter.containsKey(signature)){
                if(counter.get(signature).get() > THRESHOLD
                    && breakerCounter.get(signature).get() < THRESHOLD){    // 如果处于熔断阶段, 则直接返回null
                    log.warn("Circuit break return null, break {} times.", breakerCounter.get(signature).incrementAndGet());
                    return null;
                }
            }else{
                counter.put(signature, new AtomicInteger(0));
                breakerCounter.put(signature, new AtomicInteger(0));
            }
            retVal = pjp.proceed();
            counter.get(signature).set(0);
            breakerCounter.get(signature).set(0);;
        }catch (Throwable t){
            log.warn("Circuit breaker counter : {}, throwable : {}", counter.get(signature).incrementAndGet(), t.getMessage());
            breakerCounter.get(signature).set(0);
            throw t;
        }

        return retVal;
    }

}
