package frog.learn.spring.jpademo.dao;

import frog.learn.spring.jpademo.model.Coffee;

import java.util.List;

public interface CoffeeRepository extends BaseRepository<Coffee, Long> {
    List<Coffee> findByNameInOrderById(List<String> names);
}
