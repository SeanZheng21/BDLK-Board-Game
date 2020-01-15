package diaballik.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Map;
import java.util.Objects;

/**
 * An AI player that performs the move automatically w.r.t. the strategy
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AIPlayer extends Player {

    /**
     * The strategy to perform actions, used by the play method
     */
    @XmlTransient
    private AIPlayerStrategy strategy;

    /**
     * A map that associates the names of the strategies
     */
    static final Map<String, AIPlayerStrategy> lvMap = Map.of("Noob", new NoobAIPlayerStrategy(),
            "Starting", new StartingAIPlayerStrategy(), "Progressive", new ProgressiveAIPlayerStrategy());

    public AIPlayer(final String name, final boolean colorWhite, final String level) {
        super(name, colorWhite);
        this.strategy = lvMap.get(level);
    }

    public void playOneMove(final Game game) {
        strategy.play(game);
    }

    /**
     * Perform three actions and add a new turn to game
     * Used by the controller
     *
     * @param game the game in which to play
     */
    public void play(final Game game) {
        playOneMove(game);
        playOneMove(game);
        playOneMove(game);
        final Turn turn = new Turn(game.getTurnCount() + 1, game.getNextPlayer());
        game.setCurrentTurn(turn);
    }

    public AIPlayerStrategy getStrategy() {
        return strategy;
    }

    public AIPlayer() {
        super("Taylor", true);
        this.strategy = lvMap.get("Noob");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AIPlayer)) {
            return false;
        }
        final AIPlayer aiPlayer = (AIPlayer) o;
        return colorWhite == aiPlayer.colorWhite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorWhite);
    }
}
