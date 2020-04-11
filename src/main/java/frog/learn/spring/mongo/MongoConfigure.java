package frog.learn.spring.mongo;

import frog.learn.spring.converter.MoneyReadConverter;
import frog.learn.spring.converter.MoneyWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@Configuration
@EnableMongoRepositories
public class MongoConfigure {

    @Bean
    public MongoCustomConversions mongoCustomConversions(){
        return new MongoCustomConversions(Arrays.asList(new MoneyWriteConverter(), new MoneyReadConverter()));
    }

}
