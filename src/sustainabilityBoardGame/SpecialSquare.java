package sustainabilityBoardGame;

/**
 * Represents a non-property square on the game board with special effects, such as go (sustainability grant) and wasteland
 */
public class SpecialSquare extends BoardSquare {

    private SpecialSquareType type;

    /**
     * Creates a new special square with the specified name and type
     * @param name 
     * @param type 
     */
    public SpecialSquare(String name, SpecialSquareType type) {
        super(name);
        this.type = type;
    }

    /**
     * Returns the type of this special square
     * @return The SpecialSquareType of this square
     */
    public SpecialSquareType getType() {
        return type;
    }
}