package diaballik.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGame {
    private Game game;

    @Test
    public void testDefaultConstructor() {
        game = new Game();
        assertEquals(1, game.getTurnCount());
        assertNotNull(game.getCurrentTurn());
        assertNotNull(game.getBoard());
        assertNotNull(game.getPlayerOne());
        assertNotNull(game.getPlayerTwo());
        assertTrue(game.isOngoing());
    }

    @Test
    public void testHumanConstructor() {
        game = new Game("Human", "human1", "human2", true);

        assertEquals(1, game.getTurnCount());
        assertNotNull(game.getBoard());
        assertTrue(game.isOngoing());

        assertEquals("human1", game.getPlayerOne().getName());
        assertTrue(game.getPlayerOne() instanceof HumanPlayer);

        assertEquals("human2", game.getPlayerTwo().getName());
        assertTrue(game.getPlayerTwo() instanceof HumanPlayer);

        assertNotEquals(game.getPlayerOne().isColorWhite(), game.getPlayerTwo().isColorWhite());

        assertNotNull(game.getCurrentTurn());
    }

    @Test
    public void testAIConstructor() {
        game = new Game("Noob", "human1", "ai", true);

        assertEquals(1, game.getTurnCount());
        assertNotNull(game.getBoard());
        assertTrue(game.isOngoing());

        assertEquals("human1", game.getPlayerOne().getName());
        assertTrue(game.getPlayerOne() instanceof HumanPlayer);

        assertEquals("ai", game.getPlayerTwo().getName());
        assertTrue(game.getPlayerTwo() instanceof AIPlayer);

        assertNotEquals(game.getPlayerOne().isColorWhite(), game.getPlayerTwo().isColorWhite());

        assertNotNull(game.getCurrentTurn());
    }

    @Test
    public void testWinner() {
        game = new Game("Noob", "human1", "ai", true);
        assertTrue(game.updateOngoing());
    }

    /*
    TODO
    Test nextplayer / end turn
     */

}
