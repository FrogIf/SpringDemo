package sch.frog.learn.spring.jpademo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean   // 指示, 不要为该接口创建repository bean
public interface BaseRepository<T, Long> extends JpaRepository<T, Long> {

    /**
     * 查找前三个, 按照更新时间降序, id升序排列
     * @return 查询结果
     */
    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
