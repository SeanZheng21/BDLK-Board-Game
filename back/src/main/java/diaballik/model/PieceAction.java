package diaballik.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A piece shall be moved to the closest left, right, up, or bottom tile if free
 * (the targeted tile and its path from the current location to the targeted tile).
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PieceAction extends Action {


    /**
     * Default constructor
     */
    public PieceAction(final int id, final Tile source, final Tile destination) {
        super(id, source, destination);
    }

    public PieceAction() {
        super(-1, null, null);
    }


    /**
     * We consider that canDo has been tested and is okay
     */
    @Override
    public void execute() {
        dest.setPiece(src.getPiece());
        src.setPiece(null);
    }

    @Override
    public boolean canDo(final Turn currentTurn) {
        return src.getPosition() != dest.getPosition()
                && src.hasPiece()
                && !src.getPiece().isHasBall()
                && !dest.hasPiece()
                &&  src.getPiece().getPlayer().equals(currentTurn.getOwner())
                && validTrajectory();
    }

    /**
     * Check if the path from src to dst is clear
     * @return if the path from src to dst is clear
     */
    private boolean validTrajectory() {
        boolean res = false;
        if (src.getPosition() / Board.DIM == dest.getPosition() / Board.DIM) { //Same Line
            res = (src.getPosition() == dest.getPosition() + 1 || src.getPosition() == dest.getPosition() - 1);
        } else if (src.getPosition() % Board.DIM == dest.getPosition() % Board.DIM) { //Same column
            res = (src.getPosition() == dest.getPosition() + Board.DIM || src.getPosition() == dest.getPosition() - Board.DIM);
        }
        return res;
    }
}
