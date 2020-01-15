package diaballik.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestBallAction {

    BallAction action;
    Game game;
    Board board;


    @BeforeEach
    public void setup(){
        game = new Game("Human","Zheng","Fouque",true);
        board = new Board(game);
    }

    @Test
    public void testConstructor(){
        action = new BallAction(0,board.getTileFromXY(3,0),board.getTileFromXY(2,0));
        assertEquals(3,action.src.getPosition());
        assertEquals(2,action.dest.getPosition());

    }

    @Test
    public void testCanDo(){
        action = new BallAction(0,board.getTileFromXY(3,0),board.getTileFromXY(2,0));
        assertTrue(action.canDo(game.getCurrentTurn()));


        action = new BallAction(0,board.getTileFromXY(1,0),board.getTileFromXY(2,0));
        assertFalse(action.canDo(game.getCurrentTurn()));


        action = new BallAction(0,board.getTileFromXY(3,0),board.getTileFromXY(3,4));
        assertFalse(action.canDo(game.getCurrentTurn()));

        action = new BallAction(0,board.getTileFromXY(3,6),board.getTileFromXY(2,6));
        assertFalse(action.canDo(game.getCurrentTurn()));

        action = new BallAction(0,board.getTileFromXY(3,0),board.getTileFromXY(2,0));
        action.execute();

        new PieceAction(1,board.getTileFromXY(3,0),board.getTileFromXY(3,1)).execute();
        action = new BallAction(2,board.getTileFromXY(2,0),board.getTileFromXY(3,1));
        assertTrue(action.canDo(game.getCurrentTurn()));

        new PieceAction(1,board.getTileFromXY(1,0),board.getTileFromXY(1,1)).execute();
        action = new BallAction(2,board.getTileFromXY(2,0),board.getTileFromXY(1,1));
        assertTrue(action.canDo(game.getCurrentTurn()));
    }

    @Test
    public void testNoStrangeDiagonals(){
        (new PieceAction(0,board.getTileFromXY(0,0),board.getTileFromXY(0,1))).execute();
        (new PieceAction(1,board.getTileFromXY(0,1),board.getTileFromXY(0,2))).execute();
        (new PieceAction(1,board.getTileFromXY(1,0),board.getTileFromXY(0,0))).execute();
        (new PieceAction(2,board.getTileFromXY(2,0),board.getTileFromXY(2,1))).execute();
        (new BallAction(2,board.getTileFromXY(3,0),board.getTileFromXY(0,0))).execute();
        (new BallAction(2,board.getTileFromXY(0,0),board.getTileFromXY(0,2))).execute();
        System.out.println(game.getBoard().getTiles());
        action = new BallAction(2,board.getTileFromXY(0,2),board.getTileFromXY(6,0));
        assertFalse(action.canDo(game.getCurrentTurn()));
    }


    @Test
    public void testExecute(){
        action = new BallAction(0,board.getTileFromXY(3,0),board.getTileFromXY(2,0));
        action.execute();
        assertTrue(board.getTileFromXY(2,0).getPiece().isHasBall());


    }

}
