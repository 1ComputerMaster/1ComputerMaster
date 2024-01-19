package com.test.tester.portableServiceAdapter.dao;

import com.test.tester.portableServiceAdapter.dto.User;

public interface UserDao {
    void createUserTable();
    void insertUser(User user);
    User getUserById(Long id);
}
