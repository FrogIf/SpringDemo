package frog.learn.spring.mybatis.common.service;

import frog.learn.spring.mybatis.common.model.MCoffee;

public interface MCoffeeService {

    void save(MCoffee mCoffee);

    MCoffee queryById(Long id);
}
