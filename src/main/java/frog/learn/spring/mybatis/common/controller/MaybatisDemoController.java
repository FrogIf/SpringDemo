package frog.learn.spring.mybatis.common.controller;

import com.github.pagehelper.PageInfo;
import frog.learn.spring.constant.CommonConstant;
import frog.learn.spring.mybatis.common.model.MCoffee;
import frog.learn.spring.mybatis.common.service.MCoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("mybatis")
@RestController
@Slf4j
public class MaybatisDemoController {

    @Autowired
    private MCoffeeService mCoffeeService;

    @RequestMapping("demo")
    public String demo(){
        MCoffee mCoffee = MCoffee.builder().name("latte").price(Money.of(CurrencyUnit.of(CommonConstant.MONEY_UNIT), 10)).build();
        mCoffeeService.save(mCoffee);
        log.info("coffee : {}", mCoffee);
        MCoffee qCoffee = mCoffeeService.queryById(mCoffee.getId());
        log.info("coffee : {}", qCoffee);
        return "success";
    }

    @RequestMapping("page")
    public List<MCoffee> pageDemo(int pageNum, int pageSize){
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return mCoffeeService.findForPage(rowBounds);
    }

    @RequestMapping("page2")
    public PageInfo<MCoffee> pageInfoDemo(int pageNum, int pageSize){
        List<MCoffee> mCoffees = mCoffeeService.findForPage(pageNum, pageSize);
        PageInfo<MCoffee> pageInfo = new PageInfo<>(mCoffees);
        return pageInfo;
    }

}
