package frog.learn.spring.jdbcdemo.service.impl;

import frog.learn.spring.jdbcdemo.dao.JdbcDemoDao;
import frog.learn.spring.jdbcdemo.service.JdbcDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcDemoServiceImpl implements JdbcDemoService {

    @Autowired
    private JdbcDemoDao jdbcDemoDao;

    @Override
    public void insertDataDemo(){
        jdbcDemoDao.insertData();
    }

    @Override
    public void queryDataDemo(){
        jdbcDemoDao.listData();
    }

    @Override
    public void batchInsertDemo() {
        jdbcDemoDao.batchInsertDemo();
    }
}
