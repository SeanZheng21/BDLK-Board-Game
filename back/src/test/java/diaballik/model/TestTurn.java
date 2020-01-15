package diaballik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTurn {
    Turn turn;

    @BeforeEach
    public void setup() {
        turn = new Turn();
    }

    @Test
    public void testSetNum() {
        turn.setNum(4);
        assertEquals(4,turn.getNum());
    }

    @Test
    public void testSetOwner(){
        Player p = new HumanPlayer();
        turn.setOwner(p);
        assertEquals(p,turn.getOwner());
    }

    @Test
    public void testActionUndo(){
        Game game = new Game("Human","Fouque","Zheng",true);
        turn = new Turn(3,game.getPlayerOne());
        game.setCurrentTurn(turn);
        Action action = new PieceAction(1,game.getBoard().getTileFromXY(2,0),game.getBoard().getTileFromXY(2,1));
        assertTrue(turn.doo(action));
        assertFalse(turn.doo(action));

        assertTrue(turn.undo());
        assertFalse(turn.undo());

        assertTrue(turn.redo());
        assertFalse(turn.redo());

    }
}
