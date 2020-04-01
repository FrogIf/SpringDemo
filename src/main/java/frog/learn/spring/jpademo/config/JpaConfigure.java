package frog.learn.spring.jpademo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories  // 启动jpa
public class JpaConfigure {
}
