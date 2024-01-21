package com.test.tester.hexagonal.application.port.output;

import com.test.tester.hexagonal.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepoOutputPort extends JpaRepository<Order, Long> {
}
