package sch.frog.learn.spring.jdbcdemo.datasourcefilter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * 自定义druid datasource过滤器<br/>
 * druid 存在多个扩展点, 实现全方位的监控
 */
@Slf4j
public class ConnectionLogFilter extends FilterEventAdapter {

    @Override
    public void connection_connectBefore(FilterChain chain, Properties info){
        log.info("before connection.");
    }

    @Override
    public void connection_connectAfter(ConnectionProxy connectionProxy){
        log.info("after connection.");
    }

}
