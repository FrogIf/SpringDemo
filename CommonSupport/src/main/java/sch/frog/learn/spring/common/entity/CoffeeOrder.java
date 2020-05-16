package sch.frog.learn.spring.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import sch.frog.learn.spring.common.constant.CommonConstant;
import sch.frog.learn.spring.common.web.converter.MoneyDeserializer;
import sch.frog.learn.spring.common.web.converter.MoneySerializer;

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
public class CoffeeOrder extends BaseEntity implements Serializable {

    private String customer;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(name = "t_order_coffee")
    @OrderBy("id")
    private List<Coffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;

    /**
     * 折扣
     */
    private Integer discount;

    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = CommonConstant.MONEY_UNIT)})
    private Money total;

    private String waiter;

}
