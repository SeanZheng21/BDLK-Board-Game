package diaballik.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An action manager that manages the actions of a turn, do, undo, and redo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ActionManager {


    private static final Logger LOGGER = Logger.getAnonymousLogger();
    /**
     * Memorized performed actions
     */
    private ArrayDeque<Action> done;
    /**
     * Memorized reversed actions
     */
    private ArrayDeque<Action> undone;

    /**
     * The turn of the actions to manage
     */
    private Turn currentTurn;

    public ActionManager(final Turn currentTurn) {
        done = new ArrayDeque<>();
        undone = new ArrayDeque<>();
        this.currentTurn = currentTurn;
    }

    /**
     * Perform an action
     *
     * @param action action to perform
     * @return if the action is successfully performed
     */
    public boolean doAction(final Action action) {
        if (!action.canDo(currentTurn)) {
            return false;
        } else {
            action.execute();
            undone.clear();
            done.addFirst(action);
            return true;
        }
    }

    /**
     * Undo an action
     *
     * @return if the action is successfully performed
     */
    public boolean undo() {
        if (done.isEmpty()) {
            LOGGER.log(Level.INFO, "Nothing to Undo");
            return false;
        }
        final Action undo = done.removeFirst();
        Action reverse = null;
        try {
            reverse = undo.getClass().getConstructor(int.class, Tile.class, Tile.class).newInstance(-undo.getId(), undo.getDst(), undo.getSrc());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            LOGGER.log(Level.INFO, "Reversing crash");
            return false;
        }
        if (reverse == null || !reverse.canDo(currentTurn)) {
            LOGGER.log(Level.INFO, "Invalid Undo");
            return false;
        } else {
            reverse.execute();
            undone.addFirst(undo);
            LOGGER.log(Level.INFO, "Undo");
            return true;
        }
    }

    /**
     * Redo an action
     *
     * @return if the action is successfully reversed
     */
    public boolean redo() {
        if (undone.isEmpty()) {
            LOGGER.log(Level.INFO, "Nothing to Redo");
            return false;
        }
        final Action redo = undone.removeFirst();
        if (!redo.canDo(currentTurn)) {
            LOGGER.log(Level.INFO, "Invalid Redo");
            return false;
        } else {
            redo.execute();
            done.addFirst(redo);
            LOGGER.log(Level.INFO, "Redo");
            return true;
        }
    }

    public int actionCount() {
        return done.size();
    }
}
