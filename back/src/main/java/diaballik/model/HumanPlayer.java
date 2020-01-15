package diaballik.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A human player, moves are made manually w/ the controller's endpoints
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HumanPlayer extends Player {

    /**
     * Default constructor
     */
    public HumanPlayer(final String name, final boolean colorWhite) {
        super(name, colorWhite);
    }

    public HumanPlayer() {
        // Default constructor for marshalling
        this("Taylor", true);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HumanPlayer)) {
            return false;
        }
        final HumanPlayer that = (HumanPlayer) o;
        return colorWhite == that.colorWhite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorWhite);
    }
}
