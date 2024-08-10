package com.course.demo.dao;

import com.course.demo.entities.Officer;
import com.course.demo.entities.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class JdbcOfficerDAO implements OfficerDAO{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertOfficer;

    private final RowMapper<Officer> officerMapper =
            (rs, rowNum) -> new Officer(rs.getInt("id"),
                    Rank.valueOf(rs.getString("rank")),
                    rs.getString("first_name"),
                    rs.getString("last_name"));


    @Autowired // autowired available only  in managed beans comp,ser,rep...
    public JdbcOfficerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertOfficer = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("officers")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Officer save(Officer officer) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(officer);
        Number newId = insertOfficer.executeAndReturnKey(parameters);
        return new Officer(newId.intValue(),
                officer.rank(),
                officer.firstName(),
                officer.lastName());
    }

    @Override
    public Optional<Officer> findById(Integer id) {
        try(Stream<Officer> stream =
                jdbcTemplate.queryForStream(
                        "select * from officers where id=?",
                        officerMapper,
                        id
                )) {
            return stream.findFirst();

        }
    }

    @Override
    public List<Officer> findAll() {
        return jdbcTemplate.query("select * from officers",officerMapper);
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(
                "select count(*) from officers",Long.class);
    }

    @Override
    public void delete(Officer officer) {
        jdbcTemplate.update("delete from officers where id=?",officer.id());
    }

    @Override
    public boolean existsById(Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT 1 from officers where id=?)",Boolean.class,id);
    }
}
