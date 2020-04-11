package frog.learn.spring;

import frog.learn.spring.converter.MoneyReadConverter;
import frog.learn.spring.converter.MoneyWriteConverter;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.dialect.Dialect;
import org.springframework.data.r2dbc.function.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.Arrays;

@Configuration
@EnableR2dbcRepositories
public class R2DBCConfigure extends AbstractR2dbcConfiguration {

    @Value(value = "${spring.datasource.username}")
    private String username;

    @Value(value = "${spring.datasource.url}")
    private String url;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        // H2ConnectionConfiguration中会自动拼接"jdbc:h2:", 所以需要把原来的去掉
        this.url = this.url.substring(this.url.indexOf(':') + 1);
        this.url = this.url.substring(this.url.indexOf(':') + 1);
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder().url(url).username(username).build());
    }

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(){
        Dialect dialect = getDialect(connectionFactory());
        CustomConversions.StoreConversions conversion = CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder());
        return new R2dbcCustomConversions(conversion, Arrays.asList(new MoneyReadConverter(), new MoneyWriteConverter()));
    }

}
