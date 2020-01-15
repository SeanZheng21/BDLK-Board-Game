package diaballik.model;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class TestMarshalling {
    <T> T marshall(final T objectToMarshall) throws IOException, JAXBException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
        properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);

        final JAXBContext ctx = JAXBContextFactory.createContext(new Class[]{objectToMarshall.getClass()}, properties);
        final Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        final StringWriter sw = new StringWriter();
        marshaller.marshal(objectToMarshall, sw);
        marshaller.marshal(objectToMarshall, System.out);

        final Unmarshaller unmarshaller = ctx.createUnmarshaller();
        final StringReader reader = new StringReader(sw.toString());
        final T o = (T) unmarshaller.unmarshal(reader);

        sw.close();
        reader.close();

        return o;
    }

    // Player p1;

    @BeforeEach
    void setUp() {
        // p1 = new Player("foo", "red");
    }

    @Test
    void testHumanPlayer() {
        Player player = new HumanPlayer("Taylor Swift", true);
        try {
            player = marshall(player);
            assertEquals("Taylor Swift", player.getName());
            assertTrue(player.isColorWhite());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void testAIPlayer() {
        Player player = new AIPlayer("Taylor Swift", true, "Noob");
        try {
            player = marshall(player);
            assertEquals("Taylor Swift", player.getName());
            assertTrue(player.isColorWhite());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void testPiece() {
        Piece piece = new Piece();
        Player player = new HumanPlayer("Taylor Swift", true);
        try {
            player = marshall(player);
            piece.setPlayer(player);
            piece = marshall(piece);
            assertFalse(piece.isHasBall());
            assertEquals("Taylor Swift", piece.getPlayer().getName());
            assertTrue(piece.getPlayer().isColorWhite());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void testTilePos() {
        Tile tile = new Tile(12, new Board());
        try {
            tile = marshall(tile);
            assertEquals(12, tile.getPosition());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void testTileHasPiece() {
        Tile tile = new Tile(12, new Board());
        try {
            tile = marshall(tile);
            assertNull(tile.getPiece());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void testTurnNum() {
        try {
            Player player = marshall(new HumanPlayer("Taylor Swift", true));
            Turn turn = marshall(new Turn(13, player));

            assertEquals(13, turn.getNum());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTurnAct() {
        try {
            Player player = marshall(new HumanPlayer("Taylor Swift", true));
            Turn turn = marshall(new Turn(13, player));

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBoardInitTiles() {
        try {
            Board board = new Board();
            board = marshall(board);

            assertTrue(board.getTiles().isEmpty());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPieceActionId() {
        try {
            PieceAction pieceAction = new PieceAction();
            pieceAction = marshall(pieceAction);

            assertEquals(-1, pieceAction.getId());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPieceActionSrc() {
        try {
            PieceAction pieceAction = new PieceAction();
            pieceAction.setSrc(new Tile(4, null));
            pieceAction = marshall(pieceAction);

            assertEquals(4, pieceAction.getSrc().getPosition());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPieceActionSDst() {
        try {
            PieceAction pieceAction = new PieceAction();
            pieceAction.setDst(marshall(new Tile(4, null)));
            pieceAction = marshall(pieceAction);

            assertEquals(4, pieceAction.getDst().getPosition());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPBallActionId() {
        try {
            BallAction ballAction = new BallAction();
            ballAction = marshall(ballAction);

            assertEquals(-1, ballAction.getId());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBallActionSrc() {
        try {
            BallAction ballAction = new BallAction();
            ballAction.setSrc(new Tile(4, null));
            ballAction = marshall(ballAction);

            assertEquals(4, ballAction.getSrc().getPosition());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBallActionDst() {
        try {
            BallAction ballAction = new BallAction();
            ballAction.setDst(new Tile(4, null));
            ballAction = marshall(ballAction);

            assertEquals(4, ballAction.getDst().getPosition());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGameOngoing() {
        Game game = new Game("Human", "Jim", "Taylor", true);
        try {
            game = marshall(game);
            assertTrue(game.isOngoing());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGamePlayerOne() {
        Game game = new Game("Human", "Jim", "Taylor", true);
        try {
            game = marshall(game);
            assertEquals("Jim", game.getPlayerOne().getName());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGamePlayerTwo() {
        Game game = new Game("Human", "Jim", "Taylor", true);
        try {
            game = marshall(game);
            assertEquals("Taylor", game.getPlayerTwo().getName());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGameBoard() {
        Game game = new Game("Human", "Jim", "Taylor", true);
        try {
            game = marshall(game);
            assertFalse(game.getBoard().getTiles().isEmpty());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGameTurnCount() {
        Game game = new Game("Human", "Jim", "Taylor", true);
        try {
            game = marshall(game);
            assertEquals(1, game.getTurnCount());
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
