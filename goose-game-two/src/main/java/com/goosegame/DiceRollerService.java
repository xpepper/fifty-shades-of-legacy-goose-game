package com.goosegame;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class DiceRollerService {

    private final String url;
    private final OkHttpClient httpClient;

    public DiceRollerService() {
        url = "http://roll.diceapi.com/json/d6/d6";
        httpClient = new OkHttpClient();
    }

    public JSONObject roll() {
        Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {
            String string = response.body().string();
            return new JSONObject(string);
        } catch (IOException e) {
            throw new RuntimeException("cannot roll dice!", e);
        }
//        return new Random().nextInt(5) + 1;
    }
}