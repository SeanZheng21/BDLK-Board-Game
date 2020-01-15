package diaballik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestHumanPlayer {

    HumanPlayer player;

    @BeforeEach
    void setup() {
        player = new HumanPlayer();
    }

    @Test
    void testSetName() {
        player.setName("human");
        assertEquals("human", player.getName());
    }

    @Test
    void testSetColorWhite() {

    }

    @Test
    void testSetTurns() {

    }

    @Test
    void testAddTurn() {

    }

    @Test
    void testEquals() {

    }
}