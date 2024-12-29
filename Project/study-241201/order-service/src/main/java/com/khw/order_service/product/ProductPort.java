package com.khw.order_service.product;

public interface ProductPort {

    void save(Product product);

    Product getProduct(long productID);
}
