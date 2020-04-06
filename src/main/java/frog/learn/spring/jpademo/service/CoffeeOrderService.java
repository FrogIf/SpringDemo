package frog.learn.spring.jpademo.service;

import frog.learn.spring.jpademo.model.Coffee;
import frog.learn.spring.jpademo.model.CoffeeOrder;
import frog.learn.spring.jpademo.model.OrderState;

import java.util.List;

public interface CoffeeOrderService {

    CoffeeOrder createOrder(String customer, Coffee... coffees);

    boolean updateState(CoffeeOrder order, OrderState state);

    void initOrders();

    void findOrders();

    List<CoffeeOrder> findAllByCustomer(String customer);
}
