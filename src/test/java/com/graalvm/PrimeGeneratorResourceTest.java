package com.graalvm;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PrimeGeneratorResourceTest {

    @Test
    public void testJsPrimeGenerator() {
        given()
          .when().get("/primes/js/1000")
          .then()
             .statusCode(200);
    }

    @Test
    public void testNativePrimeGenerator() {
        given()
                .when().get("/primes/native/1000")
                .then()
                .statusCode(200);
    }

}