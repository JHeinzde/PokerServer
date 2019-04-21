package org.bonn.pokerserver.poker.interalapi.dto;

public class PlayerDTO {

    private final String name;
    private final String id;

    public PlayerDTO(String name, String id){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
