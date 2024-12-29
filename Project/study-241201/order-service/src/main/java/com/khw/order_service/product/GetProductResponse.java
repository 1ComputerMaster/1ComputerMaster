package com.khw.order_service.product;

import com.khw.order_service.product.DiscountPolicy;
import org.springframework.util.Assert;

public record GetProductResponse(long id,
                                 String name,
                                 int price,
                                 DiscountPolicy discountPolicy) {
    public GetProductResponse(long id, String name, int price, DiscountPolicy discountPolicy) {
        Assert.notNull(id, "상품 ID는 필수 입니다.");
        Assert.hasText(name, "상품 이름은 필수 입니다.");
        Assert.notNull(discountPolicy, "할인 정책은 필수 입니다.");

        this.id = id;
        this.name = name;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }
}
