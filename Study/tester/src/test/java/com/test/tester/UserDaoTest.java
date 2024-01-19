package com.test.tester;

import com.test.tester.portableServiceAdapter.dao.UserDao;
import com.test.tester.portableServiceAdapter.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testUserDao() {
        userDao.createUserTable();

        User user = new User(1L, "John Doe");
        userDao.insertUser(user);

        User retrievedUser = userDao.getUserById(1L);

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getName()).isEqualTo("John Doe");
    }
}