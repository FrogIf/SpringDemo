package frog.learn.spring;

import frog.learn.spring.common.web.converter.MoneyReadConverter;
import frog.learn.spring.common.web.converter.MoneyWriteConverter;
import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.reactor.anno.R2DBCRepository;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.dialect.Dialect;
import org.springframework.data.r2dbc.function.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;

@Configuration
@EnableR2dbcRepositories(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = R2DBCRepository.class)
)
public class ReactiveConfigure extends AbstractR2dbcConfiguration {

    @Value(value = "${spring.datasource.username}")
    private String username;

    @Value(value = "${spring.datasource.url}")
    private String url;

    @Value(value = "${spring.datasource.password}")
    private String password;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        // H2ConnectionConfiguration中会自动拼接"jdbc:h2:", 所以需要把原来的去掉
        this.url = this.url.substring(this.url.indexOf(':') + 1);
        this.url = this.url.substring(this.url.indexOf(':') + 1);
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder().url(url).username(username).password(password).build());
    }

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(){
        Dialect dialect = getDialect(connectionFactory());
        CustomConversions.StoreConversions conversion = CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder());
        return new R2dbcCustomConversions(conversion, Arrays.asList(new MoneyReadConverter(), new MoneyWriteConverter()));
    }

    @Bean
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(ReactiveRedisConnectionFactory connectionFactory){
        return new ReactiveStringRedisTemplate(connectionFactory);
    }

    /**
     * 自定义redis reactive template, 改变coffee序列化方式
     */
    @Bean
    public ReactiveRedisTemplate<String, Coffee> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory){
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Coffee> valueSerializer = new Jackson2JsonRedisSerializer<Coffee>(Coffee.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Coffee> builder
                = RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, Coffee> context = builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

}
