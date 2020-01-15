package diaballik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestNoobAI {

    Game game;
    AIPlayer aiPlayer;

    @BeforeEach
    void setUp() {
        game = new Game("Noob", "Zheng", "Taylor", false);

    }

    @Test
    void testGetName() {
        assertEquals("Taylor", game.getPlayerTwo().getName());
    }

    @Test
    void testIsColorWhite() {
        assertTrue(game.getPlayerTwo().isColorWhite());
    }

    @Test
    void testNoobStrategy() {
        assertTrue(game.getPlayerTwo() instanceof AIPlayer);
        assertTrue(((AIPlayer) game.getPlayerTwo()).getStrategy() instanceof NoobAIPlayerStrategy);
    }

    @Test
    void testNoobStrategyPlay() {
        game.setCurrentTurn(new Turn(game.getTurnCount()+1,game.getNextPlayer()));
        System.out.println(game.getCurrentTurn().getOwner().name);

        game.getBoard().getTiles().get(48).setPiece(null);
        game.getBoard().getTiles().get(47).setPiece(null);
        game.getBoard().getTiles().get(46).setPiece(null);
        game.getBoard().getTiles().get(45).setPiece(null);
        game.getBoard().getTiles().get(44).setPiece(null);

        //Creating an impossible situation with less tiles just to check if the AI takes the right action
        game.getBoard().getTiles().get(43).getPiece().setHasBall(true);
        game.getBoard().getTileFromXY(0, 5).setPiece(game.getBoard().getTileFromXY(0, 0).getPiece());
        game.getBoard().getTileFromXY(0, 0).setPiece(null);

        //Should move the ball from 43 to 42
        ((AIPlayer) game.getPlayerTwo()).playOneMove(game);


        assertTrue(game.getBoard().getTileFromCoordinate(42).getPiece().isHasBall());
        assertFalse(game.getBoard().getTileFromCoordinate(43).getPiece().isHasBall());
    }
}
