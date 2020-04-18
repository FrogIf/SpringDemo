package frog.learn.spring.jpademo.service;

import frog.learn.spring.jpademo.model.Coffee;
import org.joda.money.Money;

import java.util.List;
import java.util.Optional;

public interface CoffeeService {

    /**
     * 根据名称查询coffee
     * @param name 名称, 忽略大小写
     * @return 查询结果
     */
    Optional<Coffee> findOneCoffee(String name);

    List<Coffee> findAll();

    void reloadCoffee();

    List<Coffee> getCoffeeByName(List<String> names);

    Coffee getCoffee(Long id);

    Coffee saveCoffee(String name, Money price);
}
