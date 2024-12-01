package com.khw.order_service;


public class ProductService {
    private final ProductPort productPort;

    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    public void addProduct(AddProductRequest request) {
        Product product = new Product(request.name(), request.price(), request.value());
        productPort.save(product);
    }
}
