package sch.frog.learn.spring.jpademo.dao;


import sch.frog.learn.spring.common.entity.Coffee;

import java.util.List;

public interface CoffeeRepository extends BaseRepository<Coffee, Long> {
    List<Coffee> findByNameInOrderById(List<String> names);
}
