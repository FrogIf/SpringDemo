package frog.learn.spring.reactor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("t_coffee")  // 和jpa的不一样
public class RCoffee {

    @Id // 和jpa的不一样
    private Long id;
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
