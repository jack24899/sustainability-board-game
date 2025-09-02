package sustainabilityBoardGame;

/**
 * Maintains the current state of the game, tracks whether it has ended or not
 */
public class GameState {
    
    private boolean gameRunning;

    /**
     * Checks if the game is currently running
     * @return 
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Sets the game's running state, used to start and end the game
     * @param gameRunning 
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }
}
