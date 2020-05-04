package sch.frog.learn.spring.contextdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringApplicationContextDemo implements ApplicationRunner {

    /*
     * context的继承的演示:
     *  子context可以访问到父context中的bean
     *  父context 不 可以访问到子context中的bean
     *
     *  父context中的config bean不会影响到子context中的bean, 例如代码中的aop定义在contextA中, 不会对contextB中的bean进行代理
     *  同样, 子context中config bean也不会影响到父context中
     *
     *  如果想要一个config bean的配置影响到所有父和子的bean, 以代码中的aop配置为例, 需要如下做:
     *      config bean定义在contextA(父)中
     *      contextA和contextB均开启AOP增强EnableAspectJAutoProxy和<aop:aspectj-autoproxy/>
     *
     *      也就是说, 显式的让子context从父context中获取到config bean, 应用于子context; 父context中获取到本身的config bean应用到本身
     */

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationContext contextA = new AnnotationConfigApplicationContext(BeanDemoConfig.class);

        ApplicationContext contextB = new ClassPathXmlApplicationContext(
                new String[]{"config/applicationContext.xml"},
                contextA    // 指定父context
        );

        log.info("-------------------------");
        log.info("get bean from context A.");
        TestBean testBeanXFromA = contextA.getBean("testBeanX", TestBean.class);
        TestBean testBeanYFromA = contextA.getBean("testBeanY", TestBean.class);

        testBeanXFromA.hello();
        testBeanYFromA.hello();
//        TestBean testBeanZFromA = contextA.getBean("testBeanZ", TestBean.class);  // testBeanZ是获取不到的

        log.info("-------------------------");
        log.info("get bean from context B.");
        TestBean testBeanXFromB = contextB.getBean("testBeanX", TestBean.class);
        testBeanXFromB.hello();
        TestBean testBeanYFromB = contextB.getBean("testBeanY", TestBean.class);
        testBeanYFromB.hello();
        TestBean testBeanZFromB = contextB.getBean("testBeanZ", TestBean.class);
        testBeanZFromB.hello();

    }
}
