package sch.frog.learn.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import sch.frog.learn.spring.reactor.RedisReactorDemo;
import sch.frog.learn.spring.reactor.SimpleMongoReactorDemo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
@EnableAspectJAutoProxy
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisReactorDemo redisReactorDemo;

    @Autowired
    private SimpleMongoReactorDemo simpleMongoReactorDemo;

    /**
     * spring boot程序启动后执行
     * @param args 输入参数
     * @throws Exception 异常
     */
    @Override
    public void run(String... args) throws Exception {
        showConnection();
//        simpleMongoReactorDemo.testDemo();  // mongo simple reactor demo
//        redisReactorDemo.run(); // redis simple reactor demo
    }

    private void showConnection() throws SQLException {
        log.info(dataSource.toString());
        Connection conn = dataSource.getConnection();
        log.info(conn.toString());
        conn.close();
    }

}
