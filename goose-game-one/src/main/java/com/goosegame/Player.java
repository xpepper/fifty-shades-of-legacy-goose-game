package com.goosegame;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class Player {
    private final String name;
    private final UUID uuid;
    private int position = 0;

    public int getPosition() {
        return position;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setPosition(int position) {
        this.position = position;
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
