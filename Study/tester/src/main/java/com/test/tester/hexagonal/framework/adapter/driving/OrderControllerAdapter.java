package com.test.tester.hexagonal.framework.adapter.driving;

import com.test.tester.hexagonal.application.usecase.OrderUsecase;
import com.test.tester.hexagonal.domain.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller 정의
@RestController
@RequestMapping("/orders")
public class OrderControllerAdapter {
    private final OrderUsecase orderUsecase;

    public OrderControllerAdapter(OrderUsecase orderUsecase) {
        this.orderUsecase = orderUsecase;
    }

    @PostMapping
    public Order createOrder(@RequestBody String product) {
        return orderUsecase.createOrder(product);
    }
}