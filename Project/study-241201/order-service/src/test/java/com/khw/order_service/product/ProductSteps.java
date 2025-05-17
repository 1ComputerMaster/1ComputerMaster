package com.khw.order_service.product;


import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ProductSteps {
    public static AddProductRequest 상품등록_요청_생성(){
        final String name = "상품명";
        final int price = 1000;
        final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        return new AddProductRequest(name, price, discountPolicy);
    }

    public static ExtractableResponse<Response> 상품조회스탭(long productID) {
        return RestAssured.given().log().all()
                .when()
                .get("/products/{productID}", productID)
                .then().log().all()
                .extract();
    }
}
