package frog.learn.spring.bucks.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class NewOrderRequest {
    private String customer;
    private List<String> items;
}
