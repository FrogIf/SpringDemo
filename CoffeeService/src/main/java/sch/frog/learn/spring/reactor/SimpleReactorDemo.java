package sch.frog.learn.spring.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SimpleReactorDemo {

    public static void main(String[] args) throws InterruptedException {
        run();
    }

    public static void run() throws InterruptedException {
        Flux.range(1, 6)    // 创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
                .publishOn(Schedulers.elastic())    // 发布到弹性线程池上
                .doOnRequest(n -> log.info("request : {}", n))  // 请求事件
                .doOnComplete(() -> log.info("publisher complete 1"))    // 请求处理完成事件
                .map(i -> { // 请求处理
                    log.info("publish {}, {}", Thread.currentThread(), i);
                    return i;
                })
                .doOnComplete(() -> log.info("publisher complete task 2.")) // 请求处理事件
                .subscribeOn(Schedulers.single())   // 使用单一线程进行处理
                .onErrorResume(e -> {   // 遇到错误继续
                    log.info(e.getMessage(), e);
                    return Mono.just(-1);
                })
                .subscribe(i -> log.info("subscribe {} :{}", Thread.currentThread(), i),   // 常规消费
                        e -> log.error("error : {}", e.toString()), // 错误消费
                        () -> log.info("subscriber complete.")  // 完成消费
                        , s -> s.request(4)   // 负压, backpressure
                );

        Thread.sleep(5000);
    }
}
