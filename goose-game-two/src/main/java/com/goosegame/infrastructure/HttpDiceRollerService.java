package com.goosegame.infrastructure;

import com.goosegame.domain.DiceRollerService;
import com.goosegame.domain.Roll;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class HttpDiceRollerService implements DiceRollerService {

    private static final String DICE_SERVICE_URL = "http://roll.diceapi.com/json/d6/d6";

    private final OkHttpClient httpClient;

    public HttpDiceRollerService() {
        httpClient = new OkHttpClient();
        // This service returns something like this:
        // {"success":true,"dice":[{"value":5,"type":"d6"},{"value":1,"type":"d6"}]}
    }

    @Override
    public Roll roll() {
        Request request = new Request.Builder().url(DICE_SERVICE_URL).build();

        try (Response response = httpClient.newCall(request).execute()) {
            String diceResponse = response.body().string();
            JSONArray rollResult = new JSONObject(diceResponse).getJSONArray("dice");
            int firstThrow = firstDice(rollResult);
            int secondThrow = secondDice(rollResult);

            return new Roll(firstThrow, secondThrow);
        } catch (IOException e) {
            throw new RuntimeException("cannot roll dice!", e);
        }
    }

    private int secondDice(JSONArray rollResult) {
        return rollResult.getJSONObject(1).getInt("value");
    }

    private int firstDice(JSONArray rollResult) {
        return rollResult.getJSONObject(0).getInt("value");
    }
}