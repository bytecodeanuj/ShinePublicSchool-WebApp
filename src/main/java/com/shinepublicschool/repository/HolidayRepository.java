package com.shinepublicschool.repository;

import com.shinepublicschool.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HolidayRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HolidayRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Holiday> displayHolidays(){
        String sql="SELECT * FROM holidays";
        var rowMapper = new BeanPropertyRowMapper<>(Holiday.class); // can be used to get all fields of a Pojo class
        return jdbcTemplate.query(sql,rowMapper);
    }
}
