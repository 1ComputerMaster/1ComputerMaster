package com.test.tester.hexagonal.application.port.output;

import com.test.tester.hexagonal.domain.Order;

public interface OrderOutputPort {
    Order save(Order order);
}
