package com.khw.order_service.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity(name = "product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private int price;
    @Column(name = "`value`") // H2 예약어 문제 해결
    private String value;
    public Product (String name, int price, String value){
        Assert.hasText(name,"상품명은 필수 입니다.");
        Assert.isTrue(price > 0, "값은 0보다 커야 합니다.");
        Assert.notNull(value, "할인 정책은 필수 입니다.");
        this.name = name;
        this.price = price;
        this.value = value;
    }
}
