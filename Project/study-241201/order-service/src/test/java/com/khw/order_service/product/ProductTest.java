package com.khw.order_service.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {
    @Test
    public void update(){
        final Product product = new Product("상품명", 2000, DiscountPolicy.NONE);
        product.update("상품수정" , 2000, DiscountPolicy.NONE);
        Assertions.assertThat(product.getName()).isEqualTo("상품수정");
        Assertions.assertThat(product.getPrice()).isEqualTo(2000);
        Assertions.assertThat(product.getDiscountPolicy()).isEqualTo(DiscountPolicy.NONE);
    }
}
