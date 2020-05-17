package sch.frog.learn.spring.common.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sch.frog.learn.spring.common.entity.OrderState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStateRequest {
    private OrderState orderState;
}
