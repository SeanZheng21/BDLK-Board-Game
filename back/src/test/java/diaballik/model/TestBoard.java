package diaballik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestBoard {

    private Board board;

    @BeforeEach
    public void setup() {
        Game game = new Game();
        board = new Board(game);
    }

    @Test
    public void testConstructor() {
        assertNotNull(board.getGame());

        assertNotNull(board.getTileFromXY(0, 0));
        assertNull(board.getTileFromXY(8,12));

        assertNotNull(board.getTileFromCoordinate(4));
        assertNull(board.getTileFromCoordinate(54));

        assertEquals(board.getTileFromCoordinate(0),board.getTileFromXY(0,0));

        assertTrue(board.getTileFromXY(0, 0).hasPiece());
        assertTrue(board.getTileFromXY(3, 0).hasPiece());

        assertTrue(board.getTileFromXY(3,0).getPiece().isHasBall());
    }


}