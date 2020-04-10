package frog.learn.spring;

import frog.learn.spring.mybatis.gen.MybatisGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
@EnableCaching(proxyTargetClass = true)
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        showConnection();
//        MybatisGenerator.generate();    // 使用generator, 应用数据库表生成mybatis相关文件
    }

    private void showConnection() throws SQLException {
        log.info(dataSource.toString());
        Connection conn = dataSource.getConnection();
        log.info(conn.toString());
        conn.close();
    }

}
