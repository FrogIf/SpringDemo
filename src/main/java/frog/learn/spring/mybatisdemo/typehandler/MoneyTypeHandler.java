package frog.learn.spring.mybatisdemo.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyTypeHandler extends BaseTypeHandler<Money> {

    /**
     * 向数据库传值
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int columnIndex, Money money, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(columnIndex, money.getAmountMajorLong());
    }

    /**
     * 从数据库中取值
     */
    @Override
    public Money getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return parseMoney(resultSet.getLong(columnName));
    }

    /**
     * 从数据库中取值
     */
    @Override
    public Money getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return parseMoney(resultSet.getLong(columnIndex));
    }

    /**
     * 从数据库中取值
     */
    @Override
    public Money getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return parseMoney(callableStatement.getLong(columnIndex));
    }

    private Money parseMoney(Long value){
        return Money.of(CurrencyUnit.of("CNY"), value / 100.0);
    }
}
