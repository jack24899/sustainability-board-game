package sustainabilityBoardGame;

/**
 * Class for calculating a player's efficiency score
 * This score represents how effectively the player has invested their resources in sustainable projects 
 * 
 */
public class EfficiencyCalculator {
    
    /**
     * Calculates the efficiency score for a player based on their investments and spending
     * The efficiency score is determined by the percentage of resources that have been invested in sustainable projects relative to total spending.
     * @param player 
     * @param board 
     * @return 
     */
    public static int calculateEfficiencyScore(Player player, GameBoard board) {
        // Players current cash
        int totalResourceValue = player.getResources();

        // Add the value of all developments in properties
        for (PropertySquare property : board.getPlayerProperties(player)) {
            totalResourceValue += property.getTotalInvestment();
        }

        // Total amount the player has spent 
        int totalSpent = player.getTotalSpent();

        // What percentage of resources have been invested in sustainable projects
        int efficiency = (totalSpent > 0) ? (totalResourceValue * 100) / (totalSpent + totalResourceValue) : 0;

        return efficiency;
    }
}