package sustainabilityBoardGame;

/**
 * Abstract class representing a square on the game board
 * This class serves as the base for all types of board squares (property squares, special squares, etc)
 * It provides common functionality like naming that all board square types share
 */
public abstract class BoardSquare {
	
    // The name of the square as it appears on the board
    private String name;

    /**
     * Constructor to create a new board square
     * @param name 
     */
    public BoardSquare(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the square
     * @return 
     */
    public String getName() {
        return name;
    }
}
