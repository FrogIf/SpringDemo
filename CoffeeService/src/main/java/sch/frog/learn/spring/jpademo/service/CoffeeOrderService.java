package sch.frog.learn.spring.jpademo.service;

import sch.frog.learn.spring.common.entity.Coffee;
import sch.frog.learn.spring.common.entity.CoffeeOrder;
import sch.frog.learn.spring.common.entity.OrderState;

import java.util.List;

public interface CoffeeOrderService {

    CoffeeOrder createOrder(String customer, List<Coffee> coffees);

    boolean updateState(CoffeeOrder order, OrderState state, String starter);

    List<CoffeeOrder> findAllByCustomer(String customer);

    CoffeeOrder get(Long id);

    void initOrders();

    void findOrders();

}
