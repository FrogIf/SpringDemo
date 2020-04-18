package frog.learn.spring.bucks.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@ToString
public class NewOrderRequest {
    @NotEmpty
    private String customer;
    @NotEmpty
    private List<String> items;
}
