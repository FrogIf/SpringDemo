package frog.learn.spring.mongo.repository;

import frog.learn.spring.mongo.MongoDBRepository;
import frog.learn.spring.mongo.model.MongoCoffee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@MongoDBRepository
public interface MongoCoffeeRepository extends MongoRepository<MongoCoffee, String> {

    List<MongoCoffee> findByName(String name);

}
