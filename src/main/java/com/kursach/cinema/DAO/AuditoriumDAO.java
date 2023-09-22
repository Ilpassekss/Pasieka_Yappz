package com.kursach.cinema.DAO;

import com.kursach.cinema.Model.Auditorium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AuditoriumDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuditoriumDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Auditorium> indexAuditoriums() {

        String sql = "select * from auditorium";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Auditorium.class));
    }

    public Auditorium getAuditorium(long id){

        String sql = "select  * from auditorium where id = ?";

        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Auditorium.class)).stream().findAny().orElse(null);
    }
}
