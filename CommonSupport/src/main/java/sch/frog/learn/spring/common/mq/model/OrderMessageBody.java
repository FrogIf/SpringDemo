package sch.frog.learn.spring.common.mq.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderMessageBody {
    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单发起的客户端
     */
    private String starter;
}
