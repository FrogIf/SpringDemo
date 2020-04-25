package frog.learn.spring.mybatis.common.service;

import frog.learn.spring.mybatis.common.model.MCoffee;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MCoffeeService {

    void save(MCoffee mCoffee);

    MCoffee queryById(Long id);

    List<MCoffee> findForPage(RowBounds rowBounds);

    List<MCoffee> findForPage(int pageNum, int pageSize);
}
