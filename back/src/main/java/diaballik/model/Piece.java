package diaballik.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * A piece of a player in a tile in the board game, can hold a ball
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Piece {

    /**
     * The tile that holds the piece, can change over game state
     */
    @XmlTransient
    private Tile holder;
    /**
     * The owner of the piece, can not change over game state
     */
    private Player player;
    /**
     * If there is a ball on the piece
     */
    private boolean hasBall;

    public Tile getHolder() {
        return holder;
    }

    public void setHolder(final Tile holder) {
        this.holder = holder;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public boolean isHasBall() {
        return hasBall;
    }

    public void setHasBall(final boolean hasBall) {
        this.hasBall = hasBall;
    }

    /**
     * Default constructor
     */
    public Piece(final Tile holder, final Player player) {
        this.holder = holder;
        this.player = player;
        this.hasBall = false;
    }

    public Piece() {
        hasBall = false;
    }

}
