package frog.learn.spring.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FOO {

    private long id;

    private String bar;

}
