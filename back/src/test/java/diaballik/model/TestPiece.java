package diaballik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPiece {

    private Piece piece;

    @BeforeEach
    void setUp() {
        piece = new Piece();
    }

    @Test
    void testSetHolder() {
        Tile holder = new Tile();
        piece.setHolder(holder);
        assertEquals(holder, piece.getHolder());
    }

    @Test
    void testSetPlayer() {
        Player player = new HumanPlayer();
        piece.setPlayer(player);
        assertEquals(player, piece.getPlayer());
    }

    @Test
    void testSetHasBall() {
        piece.setHasBall(true);
        assertTrue(piece.isHasBall());
    }

    @Test
    void testCompleteConstructor() {
        Tile holder = new Tile();
        Player player = new HumanPlayer();
        piece = new Piece(holder, player);
        assertEquals(player, piece.getPlayer());
        assertFalse(piece.isHasBall());
    }
}