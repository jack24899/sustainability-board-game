package sustainabilityBoardGame;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles player creation, ordering of turns, resource distribution
 */
public class PlayerManager {
    
    private List<Player> players;
    private int currentPlayerIndex;
    private UserInterface ui;
    
    /**
     * Creates a new PlayerManager with an empty player list
     * @param ui 
     */
    public PlayerManager(UserInterface ui) {
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.ui = ui;
    }
    
    /**
     * Sets up players at the start of the game, using player count and names
     * @param initialResources 
     */
    public void initializePlayers(int initialResources) {
        // Get valid number of players within allowed range
        int numPlayers = ui.getValidPlayerCount(GameConfig.MIN_PLAYERS, GameConfig.MAX_PLAYERS);
        
        // Get names for all players
        List<String> playerNames = ui.getPlayerNames(numPlayers);
        
        // Create player objects with initial resources
        for (String name : playerNames) {
            players.add(new Player(name, initialResources));
        }
    }
    
    /**
     * Gets the player whose turn it currently is
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    /**
     * Advances to the next player's turn, in order names were entered
     */
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    
    /**
     * Gets the list of all players
     * @return 
     */
    public List<Player> getPlayers() {
        return players;
    }
    
    /**
     * Distributes resources from one player to all others, 25% of players resources are distributed
     * @param player 
     */
    public void distributeResources(Player player) {
        // Calculate 25% of player's resources to distribute
        int resourcesAmount = player.getResources() / 4;
        
        // Calculate how much each recipient gets (equally divided)
        int resourcesPerPlayer = resourcesAmount / (players.size() - 1);
        
        // Notify the distribution
        ui.displayResourceDistribution(player, resourcesAmount);
        
        // Remove resources from the distributing player
        player.removeResources(resourcesAmount, "Resource distribution");
        
        // Add resources to each recipient
        for (Player recipient : players) {
            if (recipient != player) {
                recipient.addResources(resourcesPerPlayer, "Distribution from " + player.getName());
                ui.displayResourceReceived(recipient, resourcesPerPlayer);
            }
        }
    }
    
    /**
     * Calculates the average resources of all players except the specified one to determine player wealth
     * @param excludedPlayer 
     * @return 
     */
    public double getAverageResourcesExcludingPlayer(Player excludedPlayer) {
        int totalResources = 0;
        int playerCount = 0;
        
        // Sum of resources from all other players
        for (Player player : players) {
            if (player != excludedPlayer) {
                totalResources += player.getResources();
                playerCount++;
            }
        }
        
        // Calculate average while avoiding division by zero
        return (playerCount > 0) ? totalResources / (double) playerCount : 0;
    }
}
