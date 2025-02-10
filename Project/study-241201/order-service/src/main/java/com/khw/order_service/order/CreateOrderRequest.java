package com.khw.order_service.order;

import org.springframework.util.Assert;

public record CreateOrderRequest(Long productId, int quantity) {
    public CreateOrderRequest {
        Assert.notNull(productId, "상품 ID는 있어야 함");
        Assert.isTrue(quantity > 0, "수량은 0보다 커야함");
    }
}