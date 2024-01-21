package com.test.tester.hexagonal.application.usecase;

import com.test.tester.hexagonal.domain.Order;

public interface OrderUsecase {
    Order createOrder(String product);
}