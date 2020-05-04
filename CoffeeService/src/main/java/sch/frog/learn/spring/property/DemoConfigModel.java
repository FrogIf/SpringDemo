package sch.frog.learn.spring.property;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "frog.model")
@Component
@ToString
@Data
public class DemoConfigModel {

    private int value = -1;

    private ChildModel childModel = new ChildModel();

    @Data
    @ToString
    public static class ChildModel{
        private String name = "xxx";
    }
}
