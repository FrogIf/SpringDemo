package sch.frog.learn.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("sch.frog.learn.spring.**.mapper")  // 为所有mapper接口生成bean
public class MyBatisConfigure {
}
