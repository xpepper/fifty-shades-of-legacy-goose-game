package com.goosegame;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AddPlayerAPITest {
    private GooseGameApp gooseGameApp = new GooseGameApp();

    @Before
    public void setUp() throws Exception {
        gooseGameApp.start();
    }

    @After
    public void tearDown() throws Exception {
        gooseGameApp.stop();
    }

    @Test
    public void not_allow_players_with_the_same_nickname() {
        whenAddingPlayer("Paolo", "gooser").then().statusCode(201);
        whenAddingPlayer("Pietro", "gooser").then().statusCode(400);
    }

    @Test
    public void not_allow_more_than_four_players() {
        whenAddingPlayer("Paolo", "gooser").then().statusCode(201);
        whenAddingPlayer("Giorgio", "speedy").then().statusCode(201);
        whenAddingPlayer("Ezio", "gollum").then().statusCode(201);
        whenAddingPlayer("Alessandro", "sandrino").then().statusCode(201);

        whenAddingPlayer("Piero", "onepieceflow").then().statusCode(400);
    }

    private Response whenAddingPlayer(String name, String nickname) {
        JSONObject playerInfo = new JSONObject();
        playerInfo.put("name", name);
        playerInfo.put("nickname", nickname);
        return given().
                request().body(playerInfo.toString())
                .when()
                .post("/players");
    }

}