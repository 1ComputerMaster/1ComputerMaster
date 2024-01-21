package com.test.tester.hexagonal.application.port.input;

import com.test.tester.hexagonal.application.port.output.FileReaderOutputPort;
import com.test.tester.hexagonal.application.port.output.OrderOutputPort;
import com.test.tester.hexagonal.application.usecase.OrderUsecase;
import com.test.tester.hexagonal.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderInputPort implements OrderUsecase {
    private final OrderOutputPort orderOutputPort;
    private final FileReaderOutputPort fileReaderOutputPort;

    public OrderInputPort(OrderOutputPort orderOutputPort, FileReaderOutputPort fileReaderOutputPort) {
        this.orderOutputPort = orderOutputPort;
        this.fileReaderOutputPort = fileReaderOutputPort;
    }

    public Order createOrder(String product) {
        String customerName = fileReaderOutputPort.read();

        Order order = Order.builder()
                .customerName(customerName)
                .product("Product TEST")
                .build();
        return orderOutputPort.save(order);
    }
}

