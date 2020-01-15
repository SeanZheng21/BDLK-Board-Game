package diaballik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTile {

    Tile tile;

    @BeforeEach
    public void setup() {
        tile = new Tile();
    }

    @Test
    public void testEmptyConstructor() {
        assertEquals(-1, tile.getPosition());
    }

    @Test
    public void testConstructor() {
        tile = new Tile(5, new Board());
        assertEquals(5, tile.getPosition());
        assertNotNull(tile.getBoard());
    }

    @Test
    public void testHasPiece() {
        Piece p = new Piece();
        tile.setPiece(p);
        assertTrue(tile.hasPiece());
    }
}
