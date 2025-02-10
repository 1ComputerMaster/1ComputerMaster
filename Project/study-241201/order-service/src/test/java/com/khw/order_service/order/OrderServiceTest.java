package com.khw.order_service.order;

import com.khw.order_service.product.ProductService;
import com.khw.order_service.product.ProductSteps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderservice;
    @Autowired
    private ProductService productService;
    @Test
    void 상품주문(){
        //GIVEN
        productService.addProduct(ProductSteps.상품등록_요청_생성());
        final CreateOrderRequest request = 상품주문요청_생성();
        //When
        orderservice.createOrder(request);

        //Then

    }

    private static CreateOrderRequest 상품주문요청_생성() {
        final Long productId = 1L;
        final int quantity =  2;
        final CreateOrderRequest request = new CreateOrderRequest(productId, quantity);
        return request;
    }

}
