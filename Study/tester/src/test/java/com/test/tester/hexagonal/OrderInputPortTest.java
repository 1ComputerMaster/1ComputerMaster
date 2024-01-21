package com.test.tester.hexagonal;

import com.test.tester.hexagonal.application.port.input.OrderInputPort;
import com.test.tester.hexagonal.application.port.output.FileReaderOutputPort;
import com.test.tester.hexagonal.application.port.output.OrderOutputPort;
import org.junit.jupiter.api.BeforeEach;
import com.test.tester.hexagonal.domain.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class OrderInputPortTest {
    @Mock
    private OrderOutputPort orderOutputPort;

    @Mock
    private FileReaderOutputPort fileReaderOutputPort;

    private OrderInputPort orderInputPort;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        orderInputPort = new OrderInputPort(orderOutputPort,fileReaderOutputPort);
    }

    @Test
    public void testCreateOrder() {
        // Arrange
        String customerName = "Test Customer";
        String product = "Product";
        Order expectedOrder = Order.builder()
                .customerName(customerName)
                .product(product)
                .build();

        when(fileReaderOutputPort.read()).thenReturn(customerName);
        when(orderOutputPort.save(any())).thenReturn(expectedOrder);

        // Act
        Order actualOrder = orderInputPort.createOrder(product);

        // Assert
        assertEquals(expectedOrder, actualOrder);
    }
}