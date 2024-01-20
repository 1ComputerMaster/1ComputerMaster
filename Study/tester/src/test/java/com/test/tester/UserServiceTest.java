package com.test.tester;

import com.test.tester.portableServiceAdapter.dao.UserDao;
import com.test.tester.portableServiceAdapter.dto.User;
import com.test.tester.portableServiceAdapter.service.UserService;
import com.test.tester.portableServiceAdapter.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService<User,Long> userService;

    @Mock
    private UserDao userDao;

    @BeforeEach
    public void setUp(){
        userService = new UserServiceImpl(userDao);
    }

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