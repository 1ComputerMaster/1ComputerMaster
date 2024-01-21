package com.test.tester.hexagonal.framework.adapter.driven;

import com.test.tester.hexagonal.application.port.output.JpaOrderRepoOutputPort;
import com.test.tester.hexagonal.application.port.output.OrderOutputPort;
import com.test.tester.hexagonal.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public class JpaOrderAdapter implements OrderOutputPort {
    private final JpaOrderRepoOutputPort jpaOrderRepoOutputPort;
    public JpaOrderAdapter(JpaOrderRepoOutputPort jpaOrderRepoOutputPort) {
        this.jpaOrderRepoOutputPort = jpaOrderRepoOutputPort;
    }

    @Override
    public Order save(Order order) {
        return jpaOrderRepoOutputPort.save(order);
    }
}

