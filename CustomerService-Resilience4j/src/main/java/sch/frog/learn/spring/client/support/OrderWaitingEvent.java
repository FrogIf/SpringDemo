package sch.frog.learn.spring.client.support;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import sch.frog.learn.spring.common.entity.CoffeeOrder;

@Getter
@Setter
@ToString
public class OrderWaitingEvent extends ApplicationEvent {

    private CoffeeOrder order;

    public OrderWaitingEvent(CoffeeOrder order) {
        super(order);
        this.order = order;
    }
}
