package diaballik.model;


import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * An abstract player, can be a human or an AI
 */
@XmlSeeAlso({HumanPlayer.class, AIPlayer.class})
public abstract class Player {

    /**
     * The displayed name of the player, not the identifier
     */
    protected String name;
    /**
     * If the player is displayed as white in a game
     */
    protected boolean colorWhite;

    public Player(final String name, final boolean colorWhite) {
        this.name = name;
        this.colorWhite = colorWhite;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isColorWhite() {
        return colorWhite;
    }

    public void setColorWhite(final boolean colorWhite) {
        this.colorWhite = colorWhite;
    }

}
