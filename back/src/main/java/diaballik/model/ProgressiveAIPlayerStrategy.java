package diaballik.model;
import java.util.logging.Logger;

/**
 * The progressive AI starts with the “noob” level and after 4 turns shall change to the “starting” level.
 */
public class ProgressiveAIPlayerStrategy implements AIPlayerStrategy {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private AIPlayerStrategy noobAIPlayerStrategy;
    private AIPlayerStrategy startingAIPlayerStrategy;

    public ProgressiveAIPlayerStrategy() {
        noobAIPlayerStrategy = new NoobAIPlayerStrategy();
        startingAIPlayerStrategy = new StartingAIPlayerStrategy();
    }

    /**
     * Play the game as a Noob for the first 4 rounds, then change to a Starting strategy
     * @param game the game in which to play
     */
    @Override
    public void play(final Game game) {
        final AIPlayerStrategy strategy = game.getTurnCount() < 4 ? noobAIPlayerStrategy : startingAIPlayerStrategy;
        strategy.play(game);
    }
}
