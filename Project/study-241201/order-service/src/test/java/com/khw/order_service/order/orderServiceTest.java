package com.khw.order_service.order;

import com.khw.order_service.product.DiscountPolicy;
import com.khw.order_service.product.Product;
import com.khw.order_service.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class orderServiceTest {
    private OrderService orderservice;
    private OrderPort orderPort;
    private OrderRepository orderRepository;
    @BeforeEach
    void setUp(){
        orderRepository = new OrderRepository();
        final OrderPort orderPort = new OrderPort(){
            @Override
            public void save(Order order) {
                orderRepository.save(order);
            }

            @Override
            public Product getProductById(Long productId) {
                //Stubbing
                return new Product("상품명",1000, DiscountPolicy.NONE);
            }
        };
        orderservice = new OrderService(orderPort);
    }
    @Test
    void 상품주문(){
        //GIVEN
        final Long productId = 1L;
        final int quantity =  2;
        final CreateOrderRequest request = new CreateOrderRequest(productId, quantity);
        //When
        orderservice.createOrder(request);

        //Then

    }

    private record CreateOrderRequest(Long productId, int quantity) {
        private CreateOrderRequest {
            Assert.notNull(productId, "상품 ID는 있어야 함");
            Assert.isTrue(quantity > 0, "수량은 0보다 커야함");
        }
        }

    private class OrderService {
        private final OrderPort orderPort;

        public OrderService(OrderPort orderPort) {
            this.orderPort = orderPort;
        }

        public void createOrder(CreateOrderRequest request) {
            final Product product = orderPort.getProductById(request.productId);
            final Order order = new Order(product, request.quantity());

            orderPort.save(order);
        }


    }
    private class OrderAdapter implements OrderPort{
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

    private static class Order {
        private Long id;
        private final Product product;
        private final int quantity;
        private Order (final Product product,final int quantity){
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

    private class OrderRepository {
        private Map<Long, Order> persistence = new HashMap<>();
        private Long sequence = 0L;
        public void save(final Order order){
            order.assignId(++sequence);
            persistence.put(order.getId(), order);
        }
    }
    private interface OrderPort{
        public void save(Order order);
        public Product getProductById(Long productId);
    }
}
