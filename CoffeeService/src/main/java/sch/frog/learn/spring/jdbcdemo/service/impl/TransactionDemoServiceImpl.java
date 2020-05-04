package sch.frog.learn.spring.jdbcdemo.service.impl;

import sch.frog.learn.spring.jdbcdemo.exception.RollbackException;
import sch.frog.learn.spring.jdbcdemo.service.TransactionDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
public class TransactionDemoServiceImpl implements TransactionDemoService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void programmaticTransactionDemo(){
        log.info("programmatic transaction start. count : {}", getCount());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try{
                    jdbcTemplate.execute("insert into T_FOO(ID, BAR) values (99, 'aaa')");
                    log.info("programmatic transaction running. count : {}", getCount());
                    transactionStatus.setRollbackOnly();    // 强制回滚
                }catch (Exception e){
                    transactionStatus.setRollbackOnly();
                }
            }
        });
        log.info("programmatic transaction end. count : {}", getCount());
    }

    private long getCount(){
        return (long) jdbcTemplate.queryForList("select count(*) as C from T_FOO").get(0).get("C");
//        return jdbcTemplate.queryForObject("select count(*) from FOO", Long.class);
    }

    // ===============声明式事务=================

    @Transactional
    @Override
    public void insertRecord(){
        jdbcTemplate.execute("insert into T_FOO(bar) values('AAA')");
    }

    @Transactional(rollbackFor = RollbackException.class)
    @Override
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("insert into T_FOO(bar) values('AAA')");
        throw new RollbackException();
    }

    @Override
    public void invokeInsertThenRollback() throws RollbackException{
        insertThenRollback();    // 这里不会启动insertThenRollback的事务, 因为这里仅仅是调用被代理对象方法, 只有调用代理对象的方法才会开启事务
    }

    @Autowired
    private TransactionDemoService transactionDemoService;  // 注入自己

    @Override
    public void declarativeTransactionDemo(){
        /*
         * 这里面的方法, 由于都是使用transactionDemoService.XXX进行调用, 所以都会开启事务
         */
        transactionDemoService.insertRecord();
        log.info("count : {}", getCount());
        try {
            transactionDemoService.insertThenRollback();
        } catch (RollbackException e) {
            log.error(e.getMessage(), e);
        }
        log.info("count : {}", getCount());

        try {
            transactionDemoService.invokeInsertThenRollback();
        } catch (RollbackException e) {
            log.error(e.getMessage(), e);
        }
        log.info("count : {}", getCount());
    }
}
