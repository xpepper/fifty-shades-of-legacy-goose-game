package com.goosegame.domain;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import static java.util.UUID.fromString;

public class Player {
    private String name;
    private String nickname;
    private UUID uuid;
    private int position = 0;

    public Player(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
        this.uuid = UUID.randomUUID();
    }

    public int getPosition() {
        return position;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(uuid, player.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Player.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("nickname='" + nickname + "'")
                .add("uuid=" + uuid)
                .add("position=" + position)
                .toString();
    }

    public boolean hasNickname(String aNickname) {
        return nickname.equals(aNickname);
    }

    public boolean hasUUID(String playerId) {
        return uuid.equals(fromString(playerId));
    }
}
