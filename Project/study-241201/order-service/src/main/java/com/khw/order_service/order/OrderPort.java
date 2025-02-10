package com.khw.order_service.order;

import com.khw.order_service.product.Product;

public interface OrderPort{
    public void save(Order order);
    public Product getProductById(Long productId);
}