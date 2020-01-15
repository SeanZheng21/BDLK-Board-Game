package diaballik.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * A turn of a game owned by a player
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Turn {

    /**
     * Number of turn in a game
     */
    private int num;

    /**
     * Owner of the trun
     */
    @XmlTransient
    private Player owner;

    /**
     * Action manager that manages the actions of the turn
     */
//    @XmlTransient
    private ActionManager actionManager;

    /**
     * Default constructor
     */
    public Turn(final int num, final Player owner) {
        this.num = num;
        this.owner = owner;
        actionManager = new ActionManager(this);
    }

    public Turn() {
        this.num = 0;
        actionManager = new ActionManager(this);
    }

    /**
     * Do an action w/ action manager
     */
    public boolean doo(final Action action) {
        return actionManager.doAction(action);
    }

    /**
     * Undo an action w/ action manager
     */
    public boolean undo() {
        return actionManager.undo();
    }

    /**
     * Redo an action w/ action manager
     */
    public boolean redo() {
        return actionManager.redo();
    }

    public int actionCount() {
        return actionManager.actionCount();
    }

    public int getNum() {
        return num;
    }

    public void setNum(final int num) {
        this.num = num;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(final Player owner) {
        this.owner = owner;
    }

}
