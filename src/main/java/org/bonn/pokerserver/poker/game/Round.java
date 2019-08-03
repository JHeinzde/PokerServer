package org.bonn.pokerserver.poker.game;

import org.bonn.pokerserver.poker.game.entities.Pot;
import org.bonn.pokerserver.poker.game.entities.enums.Stage;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.poker.game.entities.player.PlayerAction;
import org.bonn.pokerserver.poker.websocket.events.Event;

import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * This class encapsulates all the state of a running round
 */
public class Round {
    private final Pot pot;
    private final List<Player> remainingPlayers;
    private final Stack<Event> postRoundEvents;
    private Stage stage;


    /**
     * This constructors takes the player that should be in the round and starts a new round with it.
     * Stage is PRE_FLOP and postRoundEvents is empty. Pot will be initiated with the value 0, then the initial Blinds
     * will be subtracted from the small blind and big blind players stacks and added to the pot.
     * @param playersInRound The players that should participate in the round
     */
    public Round(List<Player> playersInRound) {
        this.pot = Pot.newPot();
        this.remainingPlayers = playersInRound;
        stage = Stage.PRE_FLOP;
        this.postRoundEvents = new Stack<>();
        getInitialBlinds();
    }

    public void pushPostRoundEvent(Event event){}

    public void processAction(PlayerAction playerAction) {}

    public void advanceStage() {}

    public void showdown() {}

    private Player determineWinner() { return null; }

    private void payOutPlayer(Player player) {}

    private void executePostRoundEvents() {}

    private void getInitialBlinds() {}


    public Stage getStage() {
        return stage;
    }
}
