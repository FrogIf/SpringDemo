package frog.learn.spring.property;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * spring boot 通过spring.factories找到该配置
 */
@Slf4j
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        ClassPathResource resource = new ClassPathResource("frog-config.properties");
        try {
            PropertySource<?> ps = loader.load("FrogConfigFile", resource).get(0);
            propertySources.addFirst(ps);   // 添加一个简单的propertySource

            propertySources.addFirst(new FrogPropertySource()); // 添加一个source为frog-sch.properties的PropertySource
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
