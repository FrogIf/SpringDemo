package sch.frog.learn.spring;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 启动 redis 分布式session
 */
@EnableRedisHttpSession
public class SessionConfigure {
}
