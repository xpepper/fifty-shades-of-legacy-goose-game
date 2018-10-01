package org.goose.game;

class Player {

    private String name;
    private int position;

    Player(String name) {
            this.name = name;
            this.position = 0;
    }

    String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !obj.getClass().isInstance(this))
            return super.equals(obj);
        return getName().equals(obj.toString());
    }

    @Override
    public String toString() {
        return getName();
    }
}
