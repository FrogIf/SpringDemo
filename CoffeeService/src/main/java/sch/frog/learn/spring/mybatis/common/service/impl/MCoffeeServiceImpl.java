package sch.frog.learn.spring.mybatis.common.service.impl;

import sch.frog.learn.spring.mybatis.common.mapper.CoffeeMapper;
import sch.frog.learn.spring.mybatis.common.model.MCoffee;
import sch.frog.learn.spring.mybatis.common.service.MCoffeeService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<MCoffee> findForPage(RowBounds rowBounds) {
        return coffeeMapper.findPageWithRowBounds(rowBounds);
    }

    @Override
    public List<MCoffee> findForPage(int pageNum, int pageSize) {
        return coffeeMapper.findPageWithParam(pageNum, pageSize);
    }
}
