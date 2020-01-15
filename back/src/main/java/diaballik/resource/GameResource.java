package diaballik.resource;

import diaballik.model.AIPlayer;
import diaballik.model.BallAction;
import diaballik.model.Game;
import diaballik.model.PieceAction;
import diaballik.model.Turn;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The resource that holds REST endpoints
 */
@Singleton
@Path("game")
@Api(value = "game")
public class GameResource {

    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private Game game;

    public GameResource() {
        super();
    }

    /**
     * Creates a new game, called when "New game" button is clicked
     *
     * @param nameOne        name of the first player in the first name field
     * @param nameTwo        name of the second player in the second name field
     * @param mode           the mode of the game, selected by  users
     * @param playerOneWhite if the 1st player is white, depends on the color choice
     * @return created game
     */
    @POST
    @Path("create/{nameOne}/{nameTwo}/{mode}/{playerOneWhite}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createGame(@PathParam("nameOne") final String nameOne,
                               @PathParam("nameTwo") final String nameTwo,
                               @PathParam("mode") final String mode,
                               @PathParam("playerOneWhite") final boolean playerOneWhite) {
        LOGGER.log(Level.CONFIG, "Creating a game: mode: " + mode + ", player 1: " + nameOne + ", player 2: "
                + nameTwo);
        game = new Game(mode, nameOne, nameTwo, playerOneWhite);
        System.out.println();
        return Response.status(Response.Status.OK).entity(game).build();
    }

    /**
     * Undo an action, called when "Undo" button is clicked
     * //     * @param game the game to undo
     *
     * @return the reversed game
     */
    @PUT
    @Path("undo/")
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response undo() {
        LOGGER.log(Level.INFO, "Undo game");
        if (game.getCurrentTurn().undo()) {
            game.setAction(game.getAction() - 1);
            return Response.status(Response.Status.OK).entity(game).build();
        } else {
            LOGGER.log(Level.SEVERE, "Nooo! Can't undo");
            return Response.status(Response.Status.BAD_REQUEST).entity(game).build();
        }
    }

    /**
     * Redo an action, called when "Redo" button is clicked
     * //     * @param game the game to redo
     *
     * @return the forwarded game
     */
    @PUT
    @Path("redo/")
    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response redo() {
        LOGGER.log(Level.INFO, "Redo game");
        if (game.getCurrentTurn().redo()) {
            game.setAction(game.getAction() + 1);
            return Response.status(Response.Status.OK).entity(game).build();
        } else {
            LOGGER.log(Level.SEVERE, "Nooo! Can't redo");
            return Response.status(Response.Status.BAD_REQUEST).entity(game).build();
        }
    }


    /**
     * Create the next turn, called when "Next" button is clicked
     * //     * @param game the game state
     *
     * @return the game w/ the new turn
     */
    @PUT
    @Path("next/")
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response next() {
        LOGGER.log(Level.INFO, "Next round");
        final Turn turn = new Turn(game.getTurnCount() + 1, game.getNextPlayer());
        game.setCurrentTurn(turn);
        // Check if the game has finished for the next turn
        game.updateOngoing();

        // TODO check w/ AI and next turn
        if (game.getPlayerTwo() instanceof AIPlayer) {
            game.setAction(0);
            // AI plays the game if the mode is AI
            final AIPlayer aiPlayer = (AIPlayer) game.getPlayerTwo();
            aiPlayer.play(game);
            final Turn turnai = new Turn(game.getTurnCount() + 1, game.getNextPlayer());
            game.setCurrentTurn(turnai);
            game.updateOngoing();
        }

        game.setAction(0);
        return Response.status(Response.Status.OK).entity(game).build();
    }

    /**
     * Perform an action, called when two clicks happened
     * //     * @param game the game state
     *
     * @param x1 source x
     * @param y1 source y
     * @param x2 destination x
     * @param y2 destination y
     * @return the game after the action
     */
    @PUT
    @Path("action/{x1}/{y1}/{x2}/{y2}")
    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response action(@PathParam("x1") final int x1, @PathParam("y1") final int y1, @PathParam("x2") final int x2,
                           @PathParam("y2") final int y2) {
        LOGGER.log(Level.INFO, "Perform action from " + x1 + ", " + y1 + " to " + x2 + ", " + y2);
        if (game.getCurrentTurn().actionCount() < 3) {
            final BallAction ballAction = new BallAction(game.getCurrentTurn().actionCount() + 1,
                    game.getBoard().getTileFromXY(x1, y1), game.getBoard().getTileFromXY(x2, y2));
            final PieceAction pieceAction = new PieceAction(game.getCurrentTurn().actionCount() + 1,
                    game.getBoard().getTileFromXY(x1, y1), game.getBoard().getTileFromXY(x2, y2));
            if (ballAction.canDo(game.getCurrentTurn())) {
                LOGGER.log(Level.INFO, "Action is a ball action");
//            game.getCurrentTurn().addAction(ballAction);
                game.getCurrentTurn().doo(ballAction);
                game.setAction(game.getAction() + 1);
            } else if (pieceAction.canDo(game.getCurrentTurn())) {
                LOGGER.log(Level.INFO, "Action is a piece action");
//            game.getCurrentTurn().addAction(pieceAction);
                game.getCurrentTurn().doo(pieceAction);
                game.setAction(game.getAction() + 1);
            } else {
                LOGGER.log(Level.SEVERE, "FML! Action position is invalid: " + x1 + ", " + y1 + " to " + x2 + ", " + y2);
                return Response.status(Response.Status.BAD_REQUEST).entity(game).build();
            }
        } else {
            LOGGER.log(Level.SEVERE, "Nooo ! Too much actions in the turn");
            return Response.status(Response.Status.BAD_REQUEST).entity(game).build();
        }

        return Response.status(Response.Status.OK).entity(game).build();
    }
}
