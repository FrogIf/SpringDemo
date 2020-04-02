package frog.learn.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("frog.learn.spring.**.mapper")  // 为所有mapper接口生成bean
public class MyBatisConfigure {
}
