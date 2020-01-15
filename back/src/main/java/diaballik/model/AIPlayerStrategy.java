package diaballik.model;

/**
 * A concept of strategy for AI players, can be played by an AIPlayer in a game
 */
public interface AIPlayerStrategy {
    void play(final Game game);
}
