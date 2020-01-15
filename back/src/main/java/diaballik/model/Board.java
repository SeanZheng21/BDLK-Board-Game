package diaballik.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The board of a Diaballik game, has a grid of tiles (dim * dim)
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Board {

    /**
     * The dimension of the board, there are dim*dim tiles
     */
    public static final int DIM = 7;

    /**
     * The game of the board (game.board = this)
     */
    @XmlTransient
    private Game game;

    /**
     * A list of tiles, in ascending order of tile's position
     * Size should be DIM * DIM
     */
    private List<Tile> tiles;

    /**
     * Default constructor
     */
    public Board(final Game game) {
        this.game = game;
        tiles = new ArrayList<>();
        initTiles();
    }

    public Board() {
        tiles = new ArrayList<>();
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Game getGame() {
        return game;
    }

    private void initTiles() {
        final IntStream stream = IntStream.range(0, DIM * DIM);
        stream.forEach(i ->
                tiles.add(i, new Tile(i, this))
        );
        final Player playerOne = game.getPlayerOne();
        final IntStream firstStream = IntStream.range(0, DIM);
        firstStream.forEach(i -> {
            final Tile t = tiles.get(i);
            final Piece p = new Piece(t, playerOne);   // game.getPlayerOne()
            p.setHasBall(i == (DIM / 2));
            t.setPiece(p);
        });
        final Player playerTwo = game.getPlayerTwo();
        final IntStream secondStream = IntStream.range(DIM * DIM - DIM, DIM * DIM);
        secondStream.forEach(i -> {
            final Tile t = tiles.get(i);
            final Piece p = new Piece(t, playerTwo);    //game.getPlayerTwo()
            p.setHasBall(i == (DIM * DIM - ((DIM / 2) + 1)));
            t.setPiece(p);
        });
    }

    public Tile getTileFromXY(final int x, final int y) {
        final int pos = x + (DIM * y);
        if (pos >= 0 && pos < (DIM * DIM)) {
            return tiles.get(pos);
        } else {
            return null;
        }
    }

    public Tile getTileFromCoordinate(final int pos) {
        if (pos >= 0 && pos < (DIM * DIM)) {
            return tiles.get(pos);
        } else {
            return null;
        }
    }

    public List<Tile> getSegment(final int source, final int destination) {
        final int src = Math.min(source, destination);
        final int dst = Math.max(source, destination);
        final List<Tile> res = new ArrayList<>();
        final int srcX, srcY, dstX, dstY;
        srcX = src % DIM;
        srcY = src / DIM;
        dstX = dst % DIM;
        dstY = dst / DIM;
        if (srcX == dstX) {
            IntStream.rangeClosed(srcY, dstY).forEach(y -> res.add(getTileFromXY(srcX, y)));
        } else if (srcY == dstY) {
            IntStream.rangeClosed(srcX, dstX).forEach(x -> res.add(getTileFromXY(x, srcY)));
        } else {
            final int diff = dstY - srcY;
            IntStream.range(0, diff + 1).forEach(i -> {
                final Tile tile = getTileFromXY(srcX + i, srcY + i);
                res.add(tile);
            });
        }
        res.remove(res.size() - 1);
        res.remove(0);

        return res;
    }

    public boolean hasWinner() {
        final boolean b1 = getTiles().stream()
                .filter(tile -> (tile.getPosition() < Board.DIM))
                .filter(Tile::hasPiece)
                .filter(tile -> tile.getPiece().getPlayer().equals(game.getPlayerTwo()))
                .anyMatch(tile -> tile.getPiece().isHasBall());

        final boolean b2 = getTiles().stream()
                    .filter(tile -> ((tile.getPosition() >= Board.DIM * (Board.DIM - 1))))
                    .filter(Tile::hasPiece)
                    .filter(tile -> tile.getPiece().getPlayer().equals(game.getPlayerOne()))
                    .anyMatch(tile -> tile.getPiece().isHasBall());
        return b1 || b2;
    }
}
