package frog.learn.spring.bucks.controller.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderRequest {
    @NotEmpty
    private String customer;
    @NotEmpty
    private List<String> items;
}
