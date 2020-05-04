package sch.frog.learn.spring.contextdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
public class BeanDemoConfig {

    @Bean
    public TestBean testBeanX(){
        return new TestBean("X");
    }

    @Bean
    public TestBean testBeanY(){
        return new TestBean("Y");
    }

    @Bean
    public XAspect aspect(){
        return new XAspect();
    }


}
