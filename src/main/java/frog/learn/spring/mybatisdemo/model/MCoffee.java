package frog.learn.spring.mybatisdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCoffee {

    private Long id;

    private String name;

    private Money price;

    private Date createTime;

    private Date updateTime;

}
