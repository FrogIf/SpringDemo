package sch.frog.learn.spring.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sch.frog.learn.spring.common.entity.CoffeeOrder;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
