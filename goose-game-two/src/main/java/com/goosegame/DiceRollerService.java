package com.goosegame;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class DiceRollerService {

    private static final String DICE_SERVICE_URL = "http://roll.diceapi.com/json/d6/d6";

    private final OkHttpClient httpClient;

    public DiceRollerService() {
        httpClient = new OkHttpClient();
        // This service returns something like this:
        // {"success":true,"dice":[{"value":5,"type":"d6"},{"value":1,"type":"d6"}]}
    }

    public JSONObject roll() {
        Request request = new Request.Builder().url(DICE_SERVICE_URL).build();

        try (Response response = httpClient.newCall(request).execute()) {
            String rollResult = response.body().string();
            return new JSONObject(rollResult);
        } catch (IOException e) {
            throw new RuntimeException("cannot roll dice!", e);
        }
    }
}