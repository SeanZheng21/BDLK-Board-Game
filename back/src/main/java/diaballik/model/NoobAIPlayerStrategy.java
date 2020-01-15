package diaballik.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Noob AI that performs moves randomly
 */
public class NoobAIPlayerStrategy implements AIPlayerStrategy {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public NoobAIPlayerStrategy() {
        // pass
    }

    /**
     * Play the game by generating some possible actions and choose one of them randomly
     *
     * @param game the game in which to play
     */
    @Override
    public void play(final Game game) {
        LOGGER.log(Level.INFO, "Noob noob!!");
        final List<Tile> aiTiles = new ArrayList<>();
        // Find all the tiles with the AI's piece
        game.getBoard().getTiles().stream()
                .filter(Tile::hasPiece)
                .filter(tile -> tile.getPiece().getPlayer().equals(game.getCurrentTurn().getOwner()))
                .forEachOrdered(tile -> aiTiles.add(tile));

        final List<Action> randActions = new ArrayList<>();
        // Try to move the ball around
        IntStream.range(0, 49).forEach(i -> {
            IntStream.range(0, 49).forEach(j -> {
                final BallAction ballAction = new BallAction(game.getCurrentTurn().actionCount(),
                        game.getBoard().getTileFromCoordinate(i),
                        game.getBoard().getTileFromCoordinate(j));
                final PieceAction pieceAction = new PieceAction(game.getCurrentTurn().actionCount(),
                        game.getBoard().getTileFromCoordinate(i),
                        game.getBoard().getTileFromCoordinate(j));
                if (ballAction.canDo(game.getCurrentTurn())) {
                    LOGGER.log(Level.INFO, "Generating a ball action");
                    randActions.add(ballAction);
                } else if (pieceAction.canDo(game.getCurrentTurn())) {
                    LOGGER.log(Level.INFO, "Generating a piece action");
                    randActions.add(pieceAction);
                }
            });
        });
        // Get a random action from the list randActions
        if (randActions.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Empty list");
            return;
        } else {
            final int randIdx = new Random().nextInt(randActions.size());
            LOGGER.log(Level.INFO, randActions.size() + " possible actions, Noob chose option n°" + randIdx);
            final Action action = randActions.get(randIdx);
            game.getCurrentTurn().doo(action);
        }
    }


}
