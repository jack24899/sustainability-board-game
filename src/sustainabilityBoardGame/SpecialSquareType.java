package sustainabilityBoardGame;

/**
 * Defines the different types of special squares in the game
 */
public enum SpecialSquareType {
    /**
     * The starting square, players collect a sustainability grant when passing this square
     */
    GO,
    
    /**
     * Players who land here may have to:
     * 1. Miss their next turn
     * 2. Distribute a portion of their resources to other players
     * Decision is based on their relative wealth compared to other players
     */
    WASTELAND;
}