package org.openindustries.healthcare.adjudication;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AdjudicationRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"outpatient-standard\",\"claimType\":\"OUTPATIENT\",\"maxAmountMinor\":200000,\"maxProcessingDays\":15,\"priority\":1}")
                .when().post("/api/v1/adjudication-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/adjudication-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"claimType\":\"OUTPATIENT\",\"requiredMaxProcessingDays\":20,\"requiredMaxAmountMinor\":150000}")
                .when().post("/api/v1/adjudication-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"claimType\":\"DENTAL\",\"requiredMaxProcessingDays\":20,\"requiredMaxAmountMinor\":150000}")
                .when().post("/api/v1/adjudication-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
