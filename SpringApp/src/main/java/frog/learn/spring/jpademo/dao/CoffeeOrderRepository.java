package frog.learn.spring.jpademo.dao;

import frog.learn.spring.jpademo.model.CoffeeOrder;

import java.util.List;

// 这里不用加@Repository注解, 只要继承jpa 的 Repository接口就可以自动生成bean
public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {

    /**
     * 根据顾客查询订单, 按照id排序
     * @param customer 顾客姓名
     * @return 查询结果
     */
    List<CoffeeOrder> findByCustomerOrderById(String customer);

    /**
     * 根据items.name查询订单
     * @param name items.name
     * @return 返回查询结果
     */
    List<CoffeeOrder> findByItems_Name(String name);
}
