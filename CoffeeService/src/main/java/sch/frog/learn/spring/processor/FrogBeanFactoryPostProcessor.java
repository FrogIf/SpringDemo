package sch.frog.learn.spring.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.ClassUtils;
import sch.frog.spring.FrogDotRunner;

/**
 * 手动向对容器中添加bean
 * 如果bean已经存在, 则不添加, 如果不存在则添加
 */
@Slf4j
public class FrogBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private static final String beanName = "frogDotRunner";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        boolean present = ClassUtils.isPresent("sch.frog.spring.FrogDotRunner",
                FrogBeanFactoryPostProcessor.class.getClassLoader());
        if(!present){
            log.info("FrogDotRunner is not present in classpath.");
            return;
        }

        if(configurableListableBeanFactory.containsBeanDefinition(beanName)){
            log.info("{} has been defined.", beanName);
            return;
        }

        register(configurableListableBeanFactory);
    }

    // 向容器中注入bean
    private void register(ConfigurableListableBeanFactory beanFactory){
        if(beanFactory instanceof BeanDefinitionRegistry){
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(FrogDotRunner.class);

            ((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(beanName, beanDefinition);
        }else{
            beanFactory.registerSingleton(beanName, new FrogDotRunner());
        }
    }

}
