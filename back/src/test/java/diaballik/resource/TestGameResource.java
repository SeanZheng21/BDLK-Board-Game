package diaballik.resource;

import com.github.hanleyt.JerseyExtension;
import diaballik.model.AIPlayer;
import diaballik.model.AIPlayerStrategy;
import diaballik.model.Board;
import diaballik.model.Game;
import diaballik.model.HumanPlayer;
import diaballik.model.NoobAIPlayerStrategy;
import diaballik.model.StartingAIPlayerStrategy;
import diaballik.model.Tile;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGameResource {
    static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());

    @SuppressWarnings("unused")
    @RegisterExtension
    JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

    Application configureJersey() {
        return new ResourceConfig(GameResource.class)
                .register(MyExceptionMapper.class)
                .register(MoxyJsonFeature.class);
    }

    <T> T LogJSONAndUnmarshallValue(final Response res, final Class<T> classToRead) {
        res.bufferEntity();
        final String json = res.readEntity(String.class);
        log.log(Level.INFO, "JSON received: " + json);
        final T obj = res.readEntity(classToRead);
        res.close();
        return obj;
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void testNewGamePvP(final Client client, final URI baseUri) {
         final Response res = client
         	.target(baseUri)
         	.path("/game/create/Justin/Taylor/Human/true")
//                 .queryParam("nameOne", "")
//                 .queryParam("nameTwo", "Taylor")
//                 .queryParam("mode", "Human")
//                 .queryParam("playerOneWhite", true)
         	.request()
         	.post(Entity.text(""));

         assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

         final Game game = LogJSONAndUnmarshallValue(res, Game.class);

         assertNotNull(game);
         assertEquals("Justin", game.getPlayerOne().getName());
         assertEquals("Taylor", game.getPlayerTwo().getName());
         assertTrue(game.getPlayerOne() instanceof HumanPlayer);
        assertTrue(game.getPlayerTwo() instanceof HumanPlayer);
    }

    @Test
    void testNewGamePvNoob(final Client client, final URI baseUri) {
        final Response res = client
                .target(baseUri)
                .path("/game/create/Justin/Taylor/Noob/true")
//                 .queryParam("nameOne", "")
//                 .queryParam("nameTwo", "Taylor")
//                 .queryParam("mode", "Human")
//                 .queryParam("playerOneWhite", true)
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);

        assertNotNull(game);
        assertEquals("Justin", game.getPlayerOne().getName());
        assertEquals("Taylor", game.getPlayerTwo().getName());
        assertTrue(game.getPlayerOne() instanceof HumanPlayer);
        assertTrue(game.getPlayerTwo() instanceof AIPlayer);
        final AIPlayer aiPlayer = (AIPlayer) game.getPlayerTwo();
        final AIPlayerStrategy strategy = aiPlayer.getStrategy();
        log.log(Level.INFO, strategy.getClass().getName());
        assertTrue(strategy instanceof NoobAIPlayerStrategy);
    }

    @Test
    void testNewGamePvStarting(final Client client, final URI baseUri) {
        final Response res = client
                .target(baseUri)
                .path("/game/create/Justin/Taylor/Starting/true")
//                 .queryParam("nameOne", "")
//                 .queryParam("nameTwo", "Taylor")
//                 .queryParam("mode", "Human")
//                 .queryParam("playerOneWhite", true)
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);

        assertNotNull(game);
        assertEquals("Justin", game.getPlayerOne().getName());
        assertEquals("Taylor", game.getPlayerTwo().getName());
        assertTrue(game.getPlayerOne() instanceof HumanPlayer);
        assertTrue(game.getPlayerTwo() instanceof AIPlayer);
        final AIPlayer aiPlayer = (AIPlayer) game.getPlayerTwo();
        final AIPlayerStrategy strategy = aiPlayer.getStrategy();
        log.log(Level.INFO, strategy.getClass().getName());
        assertTrue(strategy instanceof NoobAIPlayerStrategy);
    }

    @Test
    void testNext(final Client client, final URI baseUri) {

        final Response create = client
                .target(baseUri)
                .path("/game/create/Justin/Taylor/Human/true")
//                 .queryParam("nameOne", "")
//                 .queryParam("nameTwo", "Taylor")
//                 .queryParam("mode", "Human")
//                 .queryParam("playerOneWhite", true)
                .request()
                .post(Entity.text(""));

        final Response move = client
                .target(baseUri)
                .path("/game/action/0/0/0/1")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        final Response move2 = client
                .target(baseUri)
                .path("/game/action/0/1/0/2")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        final Response move3 = client
                .target(baseUri)
                .path("/game/action/0/2/0/3")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        final Response res = client
                .target(baseUri)
                .path("/game/next")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);
        assertEquals(2, game.getTurnCount());
    }

    @Test
    void testWrongAction(final Client client, final URI baseUri) {
        final Response create = client
                .target(baseUri)
                .path("/game/create/Justin/Taylor/Human/true")
//                 .queryParam("nameOne", "")
//                 .queryParam("nameTwo", "Taylor")
//                 .queryParam("mode", "Human")
//                 .queryParam("playerOneWhite", true)
                .request()
                .post(Entity.text(""));

        final Response res = client
                .target(baseUri)
                .path("/game/action/0/0/3/0")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
    }

    @Test
    void testRightAction(final Client client, final URI baseUri) {
        final Response create = client
                .target(baseUri)
                .path("/game/create/Justin/Taylor/Human/true")
//                 .queryParam("nameOne", "")
//                 .queryParam("nameTwo", "Taylor")
//                 .queryParam("mode", "Human")
//                 .queryParam("playerOneWhite", true)
                .request()
                .post(Entity.text(""));

        final Response res = client
                .target(baseUri)
                .path("/game/action/0/0/0/1")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
    }


    @Test
    void testUndo(final Client client, final URI baseUri) {
        final Response create = client
                .target(baseUri)
                .path("/game/create/Justin/Taylor/Human/true")
//                 .queryParam("nameOne", "")
//                 .queryParam("nameTwo", "Taylor")
//                 .queryParam("mode", "Human")
//                 .queryParam("playerOneWhite", true)
                .request()
                .post(Entity.text(""));

        final Response move = client
                .target(baseUri)
                .path("/game/action/0/0/0/1")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        final Response res = client
                .target(baseUri)
                .path("/game/undo")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);
    }

    @Test
    void testRedo(final Client client, final URI baseUri) {
        final Response create = client
                .target(baseUri)
                .path("/game/create/Justin/Taylor/Human/true")
//                 .queryParam("nameOne", "")
//                 .queryParam("nameTwo", "Taylor")
//                 .queryParam("mode", "Human")
//                 .queryParam("playerOneWhite", true)
                .request()
                .post(Entity.text(""));

        final Response move = client
                .target(baseUri)
                .path("/game/action/0/0/0/1")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        final Response undo = client
                .target(baseUri)
                .path("/game/undo")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        final Response res = client
                .target(baseUri)
                .path("/game/redo")
                .request()
                .put(Entity.entity(new Game(), MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);
    }
}
