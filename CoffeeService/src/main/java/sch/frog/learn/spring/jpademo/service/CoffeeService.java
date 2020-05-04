package sch.frog.learn.spring.jpademo.service;

import org.joda.money.Money;
import sch.frog.learn.spring.common.entity.Coffee;

import java.util.List;
import java.util.Optional;

public interface CoffeeService {
    void reloadCoffee();

    /**
     * 根据名称查询coffee
     * @param name 名称, 忽略大小写
     * @return 查询结果
     */
    Optional<Coffee> findOneCoffee(String name);

    List<Coffee> findAll();

    List<Coffee> getCoffeeByName(List<String> names);

    Coffee getCoffee(Long id);

    Coffee saveCoffee(String name, Money price);

    long getCoffeeCount();
}
