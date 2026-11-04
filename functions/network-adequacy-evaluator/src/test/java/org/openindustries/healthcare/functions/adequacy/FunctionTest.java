package org.openindustries.healthcare.functions.adequacy;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"networkId\":\"n-1\",\"measuredGapCount\":1,\"targetGapCount\":3,\"measuredCoveragePct\":97.0,\"targetCoveragePct\":95.0}")
                .when().post("/networkAdequacyEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"networkId\":\"n-2\",\"measuredGapCount\":12,\"targetGapCount\":3,\"measuredCoveragePct\":97.0,\"targetCoveragePct\":95.0}")
                .when().post("/networkAdequacyEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
