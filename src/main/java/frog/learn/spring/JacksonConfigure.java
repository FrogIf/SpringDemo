package frog.learn.spring;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfigure {

    // 去掉实体类中的 @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }


}
