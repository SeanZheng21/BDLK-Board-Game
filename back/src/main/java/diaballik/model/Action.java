package diaballik.model;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * An abstract Action of a turn
 */
@XmlSeeAlso({PieceAction.class, BallAction.class})
public abstract class Action {

    /**
     * Id of the action, 3 in total
     */
    protected int id;
    /**
     * Source tile of the action
     */
    protected Tile src;
    /**
     * Destination tile of the action
     */
    protected Tile dest;

    public Action(final int id, final Tile source, final Tile destination) {
        this.id = id;
        this.src = source;
        this.dest = destination;
    }

    int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public Tile getSrc() {
        return src;
    }

    public void setSrc(final Tile source) {
        this.src = source;
    }

    public Tile getDst() {
        return dest;
    }

    public void setDst(final Tile destination) {
        this.dest = destination;
    }

    public int getDstPos() {
        return dest.getPosition();
    }

    public int getSrcPos() {
        return src.getPosition();
    }

    /**
     * Execute the action, used by an action manager
     */
    abstract void execute();

    /**
     * Check if the action can be performed in a turn
     * @param currentTurn the turn of the action
     * @return whether the action can be performed
     */
    abstract boolean canDo(Turn currentTurn);

}
