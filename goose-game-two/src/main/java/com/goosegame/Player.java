package com.goosegame;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class Player {
    private String name;
    private String nickname;
    private UUID uuid;
    private int position = 0;

    public Player() {
    }

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

    boolean hasNickname(String nickname) {
        return getNickname().equals(nickname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(getUuid(), player.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Player.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("uuid=" + uuid)
                .add("position=" + position)
                .toString();
    }
}
