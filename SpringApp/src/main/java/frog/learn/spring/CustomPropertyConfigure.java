package frog.learn.spring;

import frog.learn.spring.processor.FrogBeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomPropertyConfigure {

    @Bean
    public static FrogBeanFactoryPostProcessor frogBeanFactoryPostProcessor(){
        return new FrogBeanFactoryPostProcessor();
    }

    /*
     * 如果这个Bean注释掉, 会自动调用CustomConfigure Module中的bean配置
     */
//    @Bean
//    public FrogBallRunner frogBallRunner(){
//        return new FrogBallRunner("kkk");
//    }
}
