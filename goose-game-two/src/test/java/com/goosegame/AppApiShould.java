package com.goosegame;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AppApiShould {

    @Test
    public void name() {
        main.main(new String[]{});

        given().
                request().body("{\"name\": \"paolo\", \"nickname\": \"pdincau\"}")
                .when().
                post("/players").
                then().
                statusCode(201).
                body("name", equalTo("paolo"));
        given().
                request().body("{\"name\": \"paolo\", \"nickname\": \"pdincau\"}")
                .when().
                post("/players").
                then().
                statusCode(400);
    }
}
