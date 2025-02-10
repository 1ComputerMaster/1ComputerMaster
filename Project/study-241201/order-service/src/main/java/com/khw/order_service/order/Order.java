package com.khw.order_service.order;

import com.khw.order_service.product.Product;
import org.springframework.util.Assert;

public class Order {
    private Long id;
    private final Product product;
    private final int quantity;
    public Order(final Product product, final int quantity){
        Assert.notNull(product, "상품은 있어야 함");
        Assert.isTrue(quantity > 0, "수량은 0보다 커야함");
        this.product = product;
        this.quantity = quantity;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}