package org.openindustries.healthcare.provider;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ProviderResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"General Hospital North\",\"providerType\":\"HOSPITAL\",\"regionCode\":\"REG-04\"}")
                .when().post("/api/v1/providers")
                .then().statusCode(201)
                .body("status", is("CONTRACTED"))
                .body("providerType", is("HOSPITAL"))
                .extract().path("id");

        given().when().post("/api/v1/providers/" + id + "/suspend")
                .then().statusCode(200).body("status", is("SUSPENDED"));
    }
}
