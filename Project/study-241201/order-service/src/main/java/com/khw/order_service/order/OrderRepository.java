package com.khw.order_service.order;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class OrderRepository {
    private Map<Long, Order> persistence = new HashMap<>();
    private Long sequence = 0L;
    public void save(final Order order){
        order.assignId(++sequence);
        persistence.put(order.getId(), order);
    }
}