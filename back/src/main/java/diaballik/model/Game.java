package diaballik.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The main game that encapsulates a state of the game, passed around in JSON
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Game {

    /**
     * The first player of the game
     */
    private Player playerOne;

    /**
     * The second player of the game
     */
    private Player playerTwo;

    /**
     * If the game has a winner
     */
    private boolean ongoing;

    /**
     * The current turn to play
     */
    @XmlTransient
    private Turn currentTurn;

    /**
     * The board of the game
     */
    private Board board;

    /**
     * Counts how many turns have been passed
     */
    private int turnCount;

    /**
     * Counts how many action have been performed in the current turn
     */
    private int action;

    /**
     * Default constructor
     */
    public Game() {
        turnCount = 1;
        ongoing = true;
        playerOne = new HumanPlayer("Default Player One", true);
        // Create player to player game by default
        playerTwo = new HumanPlayer("Default Player Two", false);
        this.board = new Board(this);
        this.currentTurn = new Turn(turnCount, playerOne);
    }

    public Game(final String mode, final String playerOne, final String playerTwo, final boolean playerOneWhite) {
        turnCount = 1;
        ongoing = true;
        this.playerOne = new HumanPlayer(playerOne, playerOneWhite);
        switch (mode) {
            case "Noob":
                this.playerTwo = new AIPlayer(playerTwo, !playerOneWhite, "Noob");
                break;
            case "Progressive":
                this.playerTwo = new AIPlayer(playerTwo, !playerOneWhite, "Progressive");
                break;
            case "Starting":
                this.playerTwo = new AIPlayer(playerTwo, !playerOneWhite, "Starting");
                break;
            case "Human":
            default:
                this.playerTwo = new HumanPlayer(playerTwo, !playerOneWhite);
        }
        this.board = new Board(this);
        this.currentTurn = new Turn(turnCount, this.playerOne);
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(final boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public Board getBoard() {
        return board;
    }

    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Get opponent player of the current turn
     * @return the opponent, the first player if the game has finished
     */
    public Player getNextPlayer() {
        if (!ongoing) {
            return playerOne;
        }
        final Player currentPlayer = this.getCurrentTurn().getOwner();
        if (currentPlayer.equals(playerOne)) {
            return playerTwo;
        } else {
            return playerOne;
        }
    }

    public void setCurrentTurn(final Turn turn) {
        currentTurn = turn;
        turnCount++;
    }

    public void setAction(final int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    /**
     * Check if the state of the game has a winner, if the game has one, update the ongoing attribute
     * @return if the game is ongoing
     */
    public boolean updateOngoing() {
        final boolean bl = !board.hasWinner();
        ongoing = bl;
        return bl;
    }
}
