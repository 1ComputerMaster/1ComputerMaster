package com.test.tester;

import com.test.tester.portableServiceAdapter.dao.UserDao;
import com.test.tester.portableServiceAdapter.dto.User;
import com.test.tester.portableServiceAdapter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserDao userDao;

    @Test
    public void testUserService() {
        // Mock 객체를 이용하여 userDao의 행동 정의
        when(userDao.getUserById(1L)).thenReturn(new User(1L, "Mock User"));

        // UserService의 특정 메서드 호출
        User retrievedUser = userService.getUserById(1L);

        // 결과 검증
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getName()).isEqualTo("Mock User");
    }
}