package com.khw.order_service.product;

import org.springframework.util.Assert;

public record UpdateProductRequest(String name, int price, DiscountPolicy discountPolicy) {

    public UpdateProductRequest {
        Assert.hasText(name, "상품명은 필수 입니다.");
        Assert.isTrue(price > 0, "가격은 필수 입니다.");
        Assert.notNull(discountPolicy, "할인 정책은 필수 입니다.");
    }
}
