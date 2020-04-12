package frog.learn.spring.mybatis.gen.service.impl;

import frog.learn.spring.constant.CommonConstant;
import frog.learn.spring.mybatis.gen.mapper.GCoffeeMapper;
import frog.learn.spring.mybatis.gen.model.GCoffee;
import frog.learn.spring.mybatis.gen.model.GCoffeeExample;
import frog.learn.spring.mybatis.gen.service.GCoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GCoffeeServiceImpl implements GCoffeeService {

    @Autowired
    private GCoffeeMapper gCoffeeMapper;

    public void demo(){
        GCoffee espresso = new GCoffee()
                .withName("espresso")
                .withPrice(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 20.0))
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        gCoffeeMapper.insert(espresso);
        log.info("coffee : {}", espresso);

        GCoffee latte = new GCoffee()
                .withName("latte")
                .withPrice(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 30.0))
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        gCoffeeMapper.insert(latte);

        GCoffee gCoffee = gCoffeeMapper.selectByPrimaryKey(espresso.getId());
        log.info("Coffee : {}", gCoffee);

        GCoffeeExample example = new GCoffeeExample();
        example.createCriteria().andNameEqualTo("latte");
        List<GCoffee> gCoffees = gCoffeeMapper.selectByExample(example);
        gCoffees.forEach(c -> log.info("coffee : {}", c));
    }

}
