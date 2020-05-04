package sch.frog.learn.spring.reactor.model;

import sch.frog.learn.spring.common.entity.BaseEntity;
import sch.frog.learn.spring.common.entity.OrderState;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "t_order")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RCoffeeOrder extends BaseEntity implements Serializable {

    private String customer;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(name = "t_order_coffee")
    @OrderBy("id")
    private List<RCoffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;


}
