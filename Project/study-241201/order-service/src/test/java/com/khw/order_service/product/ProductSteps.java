package com.khw.order_service.product;


public class ProductSteps {
    public static AddProductRequest 상품등록_요청_생성(){
        final String name = "상품명";
        final int price = 1000;
        final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        return new AddProductRequest(name, price, discountPolicy);
    }

}
