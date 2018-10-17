package com.goosegame.api;

import com.goosegame.GooseGameApp;
import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MovePlayerAPITest {
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
    public void play_a_full_game() {
        List<String> playerIds = asList(
                addPlayer("Pietro", "gamesutra"),
                addPlayer("Paolo", "gooser"),
                addPlayer("Ivan", "ivanhoe"),
                addPlayer("Monica", "mosh-mosh"));

        for (int i = 0; ; i = (i + 1) % 4) {
            int position = move(playerIds.get(i));
            if (position == 63) break;
        }
    }

    @Test
    public void players_should_follow_the_initial_order_when_playing() {
        addPlayer("Pietro", "gamesutra");
        String second = addPlayer("Paolo", "gooser");
        addPlayer("Ivan", "ivanhoe");
        addPlayer("Monica", "mosh-mosh");

        String errorMessage =
                whenMoving(second)
                        .statusCode(400)
                        .extract().path("error");

        assertThat(errorMessage, containsString("Is not your turn"));
    }

    private int move(String playerId) {
        return whenMoving(playerId)
                .statusCode(200)
                .extract().path("position");
    }

    private ValidatableResponse whenMoving(String playerId) {
        return given()
                .post("/players/" + playerId + "/roll")
                .then();
    }

    private String addPlayer(String name, String nickname) {
        JSONObject playerInfo = new JSONObject();
        playerInfo.put("name", name);
        playerInfo.put("nickname", nickname);
        return given().
                request().body(playerInfo.toString())
                .when()
                .post("/players")
                .then()
                .statusCode(201)
                .body("name", equalTo(name))
                .extract().path("id");
    }

}