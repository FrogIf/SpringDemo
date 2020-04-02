package frog.learn.spring.mybatisdemo.mapper;

import frog.learn.spring.mybatisdemo.model.MCoffee;
import org.apache.ibatis.annotations.*;

@Mapper
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


}
