package diaballik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPieceAction {

    PieceAction action;
    Game game;
    Board board;


    @BeforeEach
    public void setup(){
        game = new Game("Human","Zheng","Fouque",true);
        board = new Board(game);
    }

    @Test
    public void testConstructor(){
        action = new PieceAction(0,board.getTileFromXY(2,0),board.getTileFromXY(2,1));
        assertEquals(2,action.src.getPosition());
        assertEquals(9,action.dest.getPosition());
    }

    @Test
    public void testCanDo(){
        action = new PieceAction(0,board.getTileFromXY(2,0),board.getTileFromXY(2,1));
        assertTrue(action.canDo(game.getCurrentTurn()));

        action = new PieceAction(0,board.getTileFromXY(2,0),board.getTileFromXY(2,2));
        assertFalse(action.canDo(game.getCurrentTurn()));

        action = new PieceAction(0,board.getTileFromXY(2,0),board.getTileFromXY(3,0));
        assertFalse(action.canDo(game.getCurrentTurn()));

        action = new PieceAction(0,board.getTileFromCoordinate(0),board.getTileFromCoordinate(6));
        assertFalse(action.canDo(game.getCurrentTurn()));
    }

    @Test
    public void testExecute(){
        action = new PieceAction(0,board.getTileFromXY(2,0),board.getTileFromXY(2,1));
        action.execute();
        assertFalse(board.getTileFromXY(2,0).hasPiece());
        assertTrue(board.getTileFromXY(2,1).hasPiece());


        action = new PieceAction(0,board.getTileFromXY(2,1),board.getTileFromXY(3,1));
        action.execute();
        assertFalse(board.getTileFromXY(2,1).hasPiece());
        assertTrue(board.getTileFromXY(3,1).hasPiece());

    }
}
