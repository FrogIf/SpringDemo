# Spring缓存抽象

## 概述

支持ConcurrentMap, EhCache等

接口:
org.springframework.cache.Cache
org.springframework.cache.CacheManager

## 常用主键

@EnableCaching      -- 启用缓存

@Cacheable      -- 如果有则从缓存中取, 如果没有则从数据库中取, 然后放入缓存中

@CacheEvict     -- 缓存清理

@CachePut       -- 直接放入换存

@Caching    -- 放入上面的多个操作

@CacheConfig    -- 用于设置缓存的名字等配置

## 注意事项

使用缓存不需要引入任何新的包, 在spring context中, 有缓存的注解, 不引入redis等, 就会使用诸如java的ConcurrentMap作为缓存实现
