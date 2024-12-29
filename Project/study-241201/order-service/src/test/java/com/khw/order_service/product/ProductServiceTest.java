package com.khw.order_service.product;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;


    @Test
    void 상품조회(){
        // 상품 등록
        productService.addProduct(ProductSteps.상품등록_요청_생성());
        final long productID = 1;
        // 상품을 조회
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .get("/products/{productID}", productID)
                .then().log().all()
                .extract();

        // 상품의 응답을 검증
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("상품명");
    }
}
