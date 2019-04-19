package org.bonn.pokerserver.poker.game.entities;

public class Player {
    private final String name;
    private final String id;

    private Player(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
