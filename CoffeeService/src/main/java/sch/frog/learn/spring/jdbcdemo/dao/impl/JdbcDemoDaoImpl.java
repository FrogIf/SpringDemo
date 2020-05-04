package sch.frog.learn.spring.jdbcdemo.dao.impl;

import sch.frog.learn.spring.jdbcdemo.dao.JdbcDemoDao;
import sch.frog.learn.spring.jdbcdemo.model.FOO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class JdbcDemoDaoImpl implements JdbcDemoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insertData(){
        // insert 方式1
        Arrays.asList("a", "b").forEach(bar -> {
            jdbcTemplate.update("insert into T_FOO(bar) values (?)", bar);
        });

        // insert 方式2
        HashMap<String, String> row = new HashMap<>();
        row.put("BAR", "d");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of d: {}", id.longValue());
    }

    @Override
    public void listData(){
        // queryForObject
        log.info("Count : {}", jdbcTemplate.queryForObject("select count(*) from T_FOO", Long.class));

        // queryForList
        List<String> list = jdbcTemplate.queryForList("select bar from T_FOO", String.class);
        list.forEach(s -> log.info(s));

        // query
        List<FOO> foos = jdbcTemplate.query("select * from T_FOO", new RowMapper<FOO>() {
            @Override
            public FOO mapRow(ResultSet resultSet, int i) throws SQLException {
                return FOO.builder().id(resultSet.getLong(1)).bar(resultSet.getString(2)).build();
            }
        });

        foos.forEach(foo -> log.info("FOO : {}", foo));
    }

    @Override
    public void batchInsertDemo() {
        // 批量插入方式1
        jdbcTemplate.batchUpdate("insert into T_FOO(bar) values (?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, "b-" + i);
            }

            @Override
            public int getBatchSize() {
                return 2;
            }
        });

        // 批量插入方式2
        List<FOO> list = new ArrayList<>();
        list.add(FOO.builder().id(100L).bar("b-100").build());
        list.add(FOO.builder().id(101L).bar("b-101").build());
        namedParameterJdbcTemplate.batchUpdate("insert into T_FOO(ID, BAR) values (:id, :bar)", SqlParameterSourceUtils.createBatch(list));
    }

}
