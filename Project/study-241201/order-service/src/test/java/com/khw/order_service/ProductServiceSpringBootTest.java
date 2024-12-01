package com.khw.order_service;


import com.khw.order_service.product.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@SpringBootTest
public class ProductServiceSpringBootTest {
    @Autowired
    private ProductService productService;

    @Test
    void 상품등록(){
        final AddProductRequest request;
        request = new AddProductRequest("상품 명", 1000, DiscountPolicy.NONE);
        productService.addProduct(request);
    }
}
