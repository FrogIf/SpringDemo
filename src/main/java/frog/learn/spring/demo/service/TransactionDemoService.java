package frog.learn.spring.demo.service;

import frog.learn.spring.demo.exception.RollbackException;
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
