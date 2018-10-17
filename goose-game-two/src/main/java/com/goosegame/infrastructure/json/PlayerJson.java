package com.goosegame.infrastructure.json;

import com.goosegame.domain.Player;
import org.json.JSONObject;

public class PlayerJson {
    public static String toJson(Player candidate) {
        JSONObject json = new JSONObject();
        json.put("id",candidate.getUuid());
        json.put("name",candidate.getName());
        json.put("nickname",candidate.getNickname());
        return json.toString();
    }
}
