package frog.learn.spring;

import frog.learn.spring.converter.MoneyReadConverter;
import frog.learn.spring.converter.MoneyWriteConverter;
import frog.learn.spring.mongo.MongoDBRepository;
import frog.learn.spring.reactor.anno.ReactiveMongoDBRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Arrays;

@Configuration
@EnableMongoRepositories(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = MongoDBRepository.class)
)
@EnableReactiveMongoRepositories(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = ReactiveMongoDBRepository.class)   // 指示只有带有这个注解的repository才会被指定为reactiveRepository
)
public class MongoConfigure {

    @Bean
    public MongoCustomConversions mongoCustomConversions(){
        return new MongoCustomConversions(Arrays.asList(new MoneyWriteConverter(), new MoneyReadConverter()));
    }

}
