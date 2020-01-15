package diaballik.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * The starting AI moves the ball randomly and studies the other player’s pieces to avoid loosing the game.
 */
public class StartingAIPlayerStrategy implements AIPlayerStrategy {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public StartingAIPlayerStrategy() {
        // pass
    }

    /**
     * Play 3 actions and create a new turn
     * @param game the game in which to play
     */
    @Override
    public void play(final Game game) {
        if (game.getCurrentTurn().actionCount() > 0) {
            playDefensive(game);
            return;
        }
        LOGGER.log(Level.INFO, "Starting starting!!");
        final List<Tile> aiTiles = getPlayerTiles(game, game.getPlayerTwo());
        // Find where the AI's ball is
        final Tile tileWithAIBall = getTileWithBall(aiTiles);
//        final List<Tile> playerTiles = getPlayerTiles(game, game.getPlayerOne());
        // Find where the player's ball is
//        final Tile tileWithPlayerBall = getTileWithBall(playerTiles);

        // RandActiion are possible actions from tileWithAIBall
        final List<Action> randActions = generateActions(game, tileWithAIBall);

//        Get a random action from the list randActions
        final int randIdx = new Random().nextInt(randActions.size());
        final Action action = randActions.get(randIdx);
        game.getCurrentTurn().doo(action);
    }


    private List<Tile> getPlayerTiles(final Game game, final Player player) {
        final List<Tile> res = new ArrayList<>();
        // Find all the tiles with the AI's piece
        IntStream.range(0, 49).forEach(i -> {
            if (game.getBoard().getTileFromCoordinate(i).hasPiece() &&
                    game.getBoard().getTileFromCoordinate(i).getPiece().getPlayer().equals(player)) {
                res.add(game.getBoard().getTileFromCoordinate(i));
            }
        });
        return res;
    }

    private Tile getTileWithBall(final List<Tile> tiles) {
        return tiles.stream()
                .filter(tile -> tile.getPiece().isHasBall())
                .findFirst()
                .orElseThrow();
    }

    private List<Action> generateActions(final Game game, final Tile src) {
        final List<Action> randActions = new ArrayList<>();
        // Try to move the ball around
        IntStream.range(0, 49).forEach(j -> {
            final BallAction ballAction = new BallAction(game.getCurrentTurn().actionCount(),
                    game.getBoard().getTileFromCoordinate(src.getPosition()),
                    game.getBoard().getTileFromCoordinate(j));
            final PieceAction pieceAction = new PieceAction(game.getCurrentTurn().actionCount(),
                    game.getBoard().getTileFromCoordinate(src.getPosition()),
                    game.getBoard().getTileFromCoordinate(j));
            if (ballAction.canDo(game.getCurrentTurn())) {
                LOGGER.log(Level.INFO, "Generating a ball action");
                randActions.add(ballAction);
            } else if (pieceAction.canDo(game.getCurrentTurn())) {
                LOGGER.log(Level.INFO, "Generating a piece action");
                randActions.add(pieceAction);
            }
        });
        return randActions;
    }

    /**
     * Blocks the human by putting a piece in front of player's ball
     * @param game the game state
     */
    public void playDefensive(final Game game) {
        LOGGER.log(Level.INFO, "Starting starting!!");
        final List<Tile> aiTiles = getPlayerTiles(game, game.getPlayerTwo());
        // Find where the AI's ball is
//        final Tile tileWithAIBall = getTileWithBall(aiTiles);
        final List<Tile> playerTiles = getPlayerTiles(game, game.getPlayerOne());
        // Find where the player's ball is
        final Tile tileWithPlayerBall = getTileWithBall(playerTiles);

        // RandActiion are possible actions from aiTiles
        final List<Action> randActions = new ArrayList<>();
        aiTiles.forEach(a -> {
            randActions.addAll(generateActions(game, a));
        });

        // Select the best action from randActions
        // Chose the position that directly blocks the opponent
        final Optional<Action> bestAction = randActions.stream()
                .filter(_act -> (_act.getDst().getPosition() % Board.DIM == tileWithPlayerBall.getPosition() % Board.DIM) &&
                        _act.getDst().getPosition() > tileWithPlayerBall.getPosition())
                .findFirst();
        if (bestAction.isPresent()) {
            LOGGER.log(Level.INFO, "Making a block move");
            final Action _a = bestAction.get();
            game.getCurrentTurn().doo(_a);
        }
        LOGGER.log(Level.INFO, "Can't make a block move");
        // Continue looking if can't make a block move

        final Optional<Action> secondBest = randActions.stream()
                .min(Comparator.comparingInt(Action::getDstPos));
        if (secondBest.isPresent()) {
            final Action _aa = secondBest.get();
            LOGGER.log(Level.INFO, "Making an approaching move");
            game.getCurrentTurn().doo(_aa);
        }
        LOGGER.log(Level.INFO, "Can't make an approaching move");


//        Get a random action from the list randActions if those two filters got nothing
        final int randIdx = new Random().nextInt(randActions.size());
        final Action action = randActions.get(randIdx);
        game.getCurrentTurn().doo(action);
    }
}
