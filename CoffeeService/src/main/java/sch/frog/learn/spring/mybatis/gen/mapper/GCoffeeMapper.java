package sch.frog.learn.spring.mybatis.gen.mapper;

import sch.frog.learn.spring.mybatis.gen.model.GCoffee;
import sch.frog.learn.spring.mybatis.gen.model.GCoffeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface GCoffeeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    long countByExample(GCoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    int deleteByExample(GCoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    @Delete({
        "delete from T_GCOFFEE",
        "where ID = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    @Insert({
        "insert into T_GCOFFEE (NAME, PRICE, ",
        "CREATE_TIME, UPDATE_TIME)",
        "values (#{name,jdbcType=VARCHAR}, #{price,jdbcType=BIGINT,typeHandler=sch.frog.learn.spring.mybatis.common.typehandler.MoneyTypeHandler}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Long.class)
    int insert(GCoffee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    int insertSelective(GCoffee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    List<GCoffee> selectByExampleWithRowbounds(GCoffeeExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    List<GCoffee> selectByExample(GCoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    @Select({
        "select",
        "ID, NAME, PRICE, CREATE_TIME, UPDATE_TIME",
        "from T_GCOFFEE",
        "where ID = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("frog.learn.spring.mybatis.gen.mapper.GCoffeeMapper.BaseResultMap")
    GCoffee selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    int updateByExampleSelective(@Param("record") GCoffee record, @Param("example") GCoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    int updateByExample(@Param("record") GCoffee record, @Param("example") GCoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    int updateByPrimaryKeySelective(GCoffee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_GCOFFEE
     *
     * @mbg.generated Thu Apr 02 13:06:42 CST 2020
     */
    @Update({
        "update T_GCOFFEE",
        "set NAME = #{name,jdbcType=VARCHAR},",
          "PRICE = #{price,jdbcType=BIGINT,typeHandler=sch.frog.learn.spring.mybatis.common.typehandler.MoneyTypeHandler},",
          "CREATE_TIME = #{createTime,jdbcType=TIMESTAMP},",
          "UPDATE_TIME = #{updateTime,jdbcType=TIMESTAMP}",
        "where ID = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(GCoffee record);
}