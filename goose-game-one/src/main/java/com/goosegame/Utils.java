package com.goosegame;

import org.json.JSONObject;

public class Utils {

    public JSONObject fromJson(String body) {
        return new JSONObject(body);
    }
}
