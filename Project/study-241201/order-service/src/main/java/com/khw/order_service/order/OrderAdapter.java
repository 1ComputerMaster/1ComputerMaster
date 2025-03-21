package com.khw.order_service.order;

import com.khw.order_service.product.Product;
import com.khw.order_service.product.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderAdapter implements OrderPort{
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    public OrderAdapter(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }
    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }
    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
}