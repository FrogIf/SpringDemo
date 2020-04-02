package frog.learn.spring.mybatis.common.service.impl;

import frog.learn.spring.mybatis.common.mapper.CoffeeMapper;
import frog.learn.spring.mybatis.common.model.MCoffee;
import frog.learn.spring.mybatis.common.service.MCoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MCoffeeServiceImpl implements MCoffeeService {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Override
    public void save(MCoffee mCoffee) {
        coffeeMapper.save(mCoffee);
    }

    @Override
    public MCoffee queryById(Long id) {
        return coffeeMapper.findById(id);
    }
}
