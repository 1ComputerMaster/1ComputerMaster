package com.test.tester.portableServiceAdapter.service.impl;

import com.test.tester.portableServiceAdapter.dao.UserDao;
import com.test.tester.portableServiceAdapter.dto.User;
import com.test.tester.portableServiceAdapter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(Long id) {
        // UserServiceImpl은 UserDao를 사용하여 작업 수행
        return userDao.getUserById(id);
    }
}