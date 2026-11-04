package org.openindustries.healthcare.coverage;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class CoverageResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"memberId\":\"11111111-1111-1111-1111-111111111111\",\"coverageLimitMinor\":500000}")
                .when().post("/api/v1/coverage")
                .then().statusCode(201).body("coverageLimitMinor", is(500000))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"claimType\":\"OUTPATIENT\",\"amountMinor\":75000}")
                .when().post("/api/v1/coverage/" + id + "/payments")
                .then().statusCode(200)
                .body("remainingAfter", is(425000));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"memberId\":\"11111111-1111-1111-1111-111111111111\",\"coverageLimitMinor\":500000}")
                .when().post("/api/v1/coverage")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"claimType\":\"OUTPATIENT\",\"amountMinor\":900000}")
                .when().post("/api/v1/coverage/" + id + "/payments")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/coverage/" + id)
                .then().statusCode(200).body("coverageLimitMinor", is(500000));
    }
}
