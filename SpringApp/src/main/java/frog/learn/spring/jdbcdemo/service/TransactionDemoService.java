package frog.learn.spring.jdbcdemo.service;

import frog.learn.spring.jdbcdemo.exception.RollbackException;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionDemoService {

    /**
     * 编程式事务演示
     */
    void programmaticTransactionDemo();

    @Transactional
    void insertRecord();

    @Transactional(rollbackFor = RollbackException.class)
    void insertThenRollback() throws RollbackException;

    void invokeInsertThenRollback() throws RollbackException;

    void declarativeTransactionDemo();
}
