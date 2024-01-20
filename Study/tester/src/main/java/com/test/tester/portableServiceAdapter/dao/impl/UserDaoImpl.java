package com.test.tester.portableServiceAdapter.dao.impl;

import com.test.tester.portableServiceAdapter.dao.UserDao;
import com.test.tester.portableServiceAdapter.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUserTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id SERIAL, name VARCHAR(255))");
    }

    @Override
    public void insertUser(User user) {
        jdbcTemplate.update("INSERT INTO users (name) VALUES (?)", user.getName());
    }

    @Override
    public User getUserById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[]{id}, (rs, rowNum) ->
                new User(rs.getLong("id"), rs.getString("name")));
    }
}