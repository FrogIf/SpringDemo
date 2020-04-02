package frog.learn.spring.mybatis.common.mapper;

import frog.learn.spring.mybatis.common.model.MCoffee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CoffeeMapper {

    @Insert("insert into t_mcoffee(name, price, create_time, update_time) values(#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)   // 使用生成的key
    int save(MCoffee coffee);    // 返回值是受更新的结果集的条数

    @Select("select * from t_mcoffee where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime")
    })
    MCoffee findById(@Param("id") Long id);

    /**
     * 分页查找信息
     * @param rowBounds
     * @return 返回列表
     */
    @Select("select * from t_mcoffee order by id")
    List<MCoffee> findPageWithRowBounds(RowBounds rowBounds);

    /**
     * 分页查找信息
     * @param pageNum 页码
     * @param pageSize 页容量
     * @return 返回列表
     */
    @Select("select * from t_mcoffee order by id")
    List<MCoffee> findPageWithParam(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
