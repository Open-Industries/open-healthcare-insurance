package org.openindustries.healthcare.functions.claimfraud;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"claimId\":\"cl-1\",\"velocityIndex\":3,\"mismatchIndex\":5,\"upcodingIndex\":2}")
                .when().post("/claimFraudScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"claimId\":\"cl-2\",\"velocityIndex\":91,\"mismatchIndex\":83,\"upcodingIndex\":74}")
                .when().post("/claimFraudScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
