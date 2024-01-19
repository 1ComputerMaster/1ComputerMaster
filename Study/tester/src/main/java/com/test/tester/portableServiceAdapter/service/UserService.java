package com.test.tester.portableServiceAdapter.service;

import com.test.tester.portableServiceAdapter.dto.User;

public interface UserService {
    User getUserById(Long id);
}