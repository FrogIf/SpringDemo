package frog.learn.spring.jdbcdemo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FOO {

    private long id;

    private String bar;

}
