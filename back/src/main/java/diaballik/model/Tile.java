package diaballik.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * A tile is a square in a board, can hold a piece or not
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tile {

    /**
     * The position of the tile in the board, can convert to (x, y)
     */
    private int position;

    /**
     * The holder of the tile
     */
    @XmlTransient
    private Board board;

    /**
     * The piece in the tile, can be null
     */
    private Piece piece;


    public Tile(final int position, final Board board) {
        this.position = position;
        this.board = board;
        // Piece is not set here, create an empty tile
    }

    public Tile() {
        this.position = -1;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(final Board board) {
        this.board = board;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(final Piece piece) {
        this.piece = piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

}
