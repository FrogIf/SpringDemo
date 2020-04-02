package frog.learn.spring.mybatisdemo.service;

import frog.learn.spring.mybatisdemo.model.MCoffee;

public interface MCoffeeService {

    void save(MCoffee mCoffee);

    MCoffee queryById(Long id);
}
