package diaballik.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A ball carried by a piece shall be moved to another piece of the same player
 * if there is a horizontal, vertical, diagonal piece-free line between these two pieces
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BallAction extends Action {

    /**
     * Default constructor
     */
    public BallAction(final int id, final Tile source, final Tile destination) {
        super(id, source, destination);
    }

    public BallAction() {
        super(-1, null, null);
    }


    @Override
    public void execute() {
        src.getPiece().setHasBall(false);
        dest.getPiece().setHasBall(true);
    }

    @Override
    public boolean canDo(final Turn currentTurn) {
        final Player currentPlayer = currentTurn.getOwner();
        return src.getPosition() != dest.getPosition()
                && dest.hasPiece()
                && src.hasPiece()
                && src.getPiece().isHasBall()
                && src.getPiece().getPlayer().equals(currentPlayer)
                && dest.getPiece().getPlayer().equals(currentPlayer)
                && validTrajectory();
    }

    private boolean validTrajectory() {
        boolean res = false;
        final Board board = src.getBoard();
        if (src.getPosition() % Board.DIM == dest.getPosition() % Board.DIM
                || (src.getPosition() / Board.DIM == dest.getPosition() / Board.DIM)
                || (src.getPosition() % (Board.DIM + 1) == dest.getPosition() % (Board.DIM + 1))
                || (src.getPosition() % (Board.DIM - 1) == dest.getPosition() % (Board.DIM - 1))) {
            res = board.getSegment(src.getPosition(), dest.getPosition()).stream().noneMatch(Tile::hasPiece);
        }
        return res;
    }
}
