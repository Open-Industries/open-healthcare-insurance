package org.openindustries.healthcare.member;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class MemberResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"fullName\":\"Marie Curie\",\"memberNumber\":\"MBR-7788990011\",\"planCode\":\"GOLD-2026\"}")
                .when().post("/api/v1/members")
                .then().statusCode(201)
                .body("status", is("ACTIVE"))
                .body("memberNumberMasked", endsWith("0011"))
                .extract().path("id");

        given().when().get("/api/v1/members/" + id)
                .then().statusCode(200).body("status", is("ACTIVE"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"fullName\":\"Marie Curie\",\"memberNumber\":\"MBR-7788990011\",\"planCode\":\"GOLD-2026\"}")
                .when().post("/api/v1/members")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/members/" + id + "/suspend")
                .then().statusCode(200).body("status", is("SUSPENDED"));
    }
}
