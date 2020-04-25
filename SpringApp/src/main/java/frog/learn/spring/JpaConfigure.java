package frog.learn.spring;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import frog.learn.spring.mongo.MongoDBRepository;
import frog.learn.spring.reactor.anno.R2DBCRepository;
import frog.learn.spring.reactor.anno.ReactiveMongoDBRepository;
import frog.learn.spring.redis.anno.CacheRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(  // 启动jpa
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = R2DBCRepository.class),  // r2dbc repository 与jpa不兼容, 需除去, 否则报错
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = CacheRepository.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = MongoDBRepository.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = ReactiveMongoDBRepository.class)
        }
)
public class JpaConfigure {

        // 去掉实体类中的 @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
        @Bean
        public Hibernate5Module hibernate5Module() {
                Hibernate5Module hibernate5Module = new Hibernate5Module();
                return hibernate5Module;
        }

}
