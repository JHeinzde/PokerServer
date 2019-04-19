package org.bonn.pokerserver.poker.game.entities.player;

public class Player {
    private final String name;
    private final String id;
    private final MoneyStack moneyStack;

    private Player(String name, String id, MoneyStack moneyStack) {
        this.name = name;
        this.id = id;
        this.moneyStack = moneyStack;
    }

    public MoneyStack getMoneyStack() {
        return moneyStack;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public static Player newPlayer(String name, String id, MoneyStack moneyStack){
        return new Player(name, id, moneyStack);
    }
}
