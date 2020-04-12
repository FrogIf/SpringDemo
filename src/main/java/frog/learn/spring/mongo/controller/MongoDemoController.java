package frog.learn.spring.mongo.controller;

import com.mongodb.client.result.UpdateResult;
import frog.learn.spring.constant.CommonConstant;
import frog.learn.spring.mongo.model.MongoCoffee;
import frog.learn.spring.mongo.repository.MongoCoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("mongo")
@Slf4j
public class MongoDemoController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoCoffeeRepository mongoCoffeeRepository;

    @RequestMapping("demo")
    public String demo() throws InterruptedException {
        MongoCoffee coffee = MongoCoffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 20.0))
                .createTime(new Date())
                .updateTime(new Date()).build();
        MongoCoffee mongoCoffee = mongoTemplate.save(coffee);
        log.info("Coffee : {}", mongoCoffee);

        List<MongoCoffee> mongoCoffees = mongoTemplate.find(Query.query(Criteria.where("name").is("espresso")), MongoCoffee.class);
        log.info("find {} coffee", mongoCoffees.size());

        mongoCoffees.forEach(c -> log.info("coffee : {}", c));

        Thread.sleep(1000);

        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("espresso")),
                new Update().set("price", Money.ofMinor(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 30)).currentDate("updateTime"), MongoCoffee.class);

        log.info("update result : {}", updateResult.getModifiedCount());
        MongoCoffee mc = mongoTemplate.findById(coffee.getId(), MongoCoffee.class);
        log.info("update : {}", mc);

        mongoTemplate.remove(mc);

        return "success";
    }

    @RequestMapping("redemo")
    public List<MongoCoffee> demoRepository(){
        MongoCoffee espresso = MongoCoffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 20.0))
                .createTime(new Date())
                .updateTime(new Date()).build();

        MongoCoffee latte = MongoCoffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 30.0))
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        mongoCoffeeRepository.insert(Arrays.asList(espresso, latte));

        latte.setPrice(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 25.0));
        latte.setUpdateTime(new Date());

        mongoCoffeeRepository.save(latte);

        List<MongoCoffee> ql = mongoCoffeeRepository.findByName("latte");
        ql.forEach(c -> log.info("coffee : {}", c));


        return mongoCoffeeRepository.findAll(Sort.by("name"));
    }

}
