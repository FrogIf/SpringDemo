package frog.learn.spring;

import frog.learn.spring.reactor.anno.R2DBCRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(  // 启动jpa
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = R2DBCRepository.class)  // r2dbc repository 与jpa不兼容, 需除去, 否则报错
)
public class JpaConfigure {
}
