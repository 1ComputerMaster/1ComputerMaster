package com.khw.order_service.product;


import com.khw.order_service.ApiTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
public class ProductApiTest extends ApiTest {
    @LocalServerPort
    private int port;
    private ProductRepository productRepository;
    private ProductPort productPort;
    private ProductService productService;
    private final Product stupProduct = new Product("상품명",2000,DiscountPolicy.NONE);

    @BeforeEach
    public void init() {
        RestAssured.port = port;
        productRepository = Mockito.mock(ProductRepository.class);
        productPort = Mockito.mock(ProductPort.class);
        productService = new ProductService(productPort);
    }

    @Test
    void 상품등록(){
        final var request = ProductSteps.상품등록_요청_생성();
        //API 요청
        final var response = 상품등록요청(request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static ExtractableResponse<Response> 상품등록요청(AddProductRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/products")
                .then()
                .log().all().extract();
    }



    @Test
    void 상품조회(){
        // 상품 등록
        productService.addProduct(ProductSteps.상품등록_요청_생성());
        final long productID = 1;
        Mockito.when(productRepository.findById(productID)).thenReturn(Optional.of(stupProduct));

        Mockito.when(productPort.getProduct(productID)).thenReturn(stupProduct);
        // 상품을 조회
        final var response = ProductSteps.상품조회스탭(productID);

        // 상품의 응답을 검증
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("상품명");
    }

}
