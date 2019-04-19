package org.bonn.pokerserver.poker.game.entities.cards;

import org.bonn.pokerserver.poker.common.interfaces.Observable;
import org.bonn.pokerserver.poker.game.entities.enums.Stage;

import java.util.Collections;
import java.util.Set;

public class Board extends Observable {
    private final Set<Card> communityCards;
    private Stage stageOfBoard;

    private Board(Set<Card> communityCards, Stage stageOfBoard) {
        super();
        this.communityCards = communityCards;
        this.stageOfBoard = stageOfBoard;
    }


    public Stage getStageOfBoard() {
        return stageOfBoard;
    }

    public Set<Card> getCommunityCards() {
        return Collections.unmodifiableSet(communityCards);
    }



    public static Board newBoard(Set<Card> communityCards, Stage stageOfBoard) {
        return new Board(communityCards, stageOfBoard);
    }

}
