package sch.frog.learn.spring.reactor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sch.frog.learn.spring.common.constant.CommonConstant;
import sch.frog.learn.spring.common.entity.BaseEntity;
import sch.frog.learn.spring.common.web.converter.MoneyDeserializer;
import sch.frog.learn.spring.common.web.converter.MoneySerializer;
import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Table("t_coffee")  // 和jpa的不一样
@Entity
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RCoffee extends BaseEntity implements Serializable{

    @Id // 和jpa的不一样
    private Long id;

    private String name;

    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value= CommonConstant.MONEY_UNIT)})
    private Money price;
}
