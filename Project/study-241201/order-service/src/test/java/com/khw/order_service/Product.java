package com.khw.order_service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.util.Assert;

@Entity(name = "product")
public record Product(@Id String name, int price, DiscountPolicy value) {
    public Product{
        Assert.hasText(name,"상품명은 필수 입니다.");
        Assert.isTrue(price > 0, "값은 0보다 커야 합니다.");
        Assert.notNull(value, "할인 정책은 필수 입니다.");
    }
}
