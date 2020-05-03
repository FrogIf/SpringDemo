package frog.learn.spring.common.web.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class NewCoffeeRequest {

    @NotEmpty   // spring 会自动进行校验
    private String name;

    @NotNull    // spring 会自动进行校验
    private Money price;

}
