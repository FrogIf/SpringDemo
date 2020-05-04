package sch.frog.learn.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sch.frog.learn.spring.mybatis.gen.MybatisGenerator;

@Component
@Order(1)
@Slf4j
public class MybatisGenerateRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==========generate mybatis xml==========");
        MybatisGenerator.generate(); // 使用generator, 应用数据库表生成mybatis相关文件
    }
}
