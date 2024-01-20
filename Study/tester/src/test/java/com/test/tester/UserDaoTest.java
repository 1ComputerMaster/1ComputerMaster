package com.test.tester;

import com.test.tester.portableServiceAdapter.dao.UserDao;
import com.test.tester.portableServiceAdapter.dao.impl.UserDaoImpl;
import com.test.tester.portableServiceAdapter.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao = new UserDaoImpl(jdbcTemplate);
    }

    @Test
    public void testUserDao() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(new User(1L, "John Doe"));

        User retrievedUser = userDao.getUserById(1L);

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getName()).isEqualTo("John Doe");
    }
}