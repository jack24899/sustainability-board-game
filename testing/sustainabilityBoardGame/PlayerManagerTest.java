package sustainabilityBoardGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerManagerTest {

	private PlayerManager playerManager;
    private Player player1;
    private Player player2;
    private Player player3;
    
    @BeforeEach
    public void setUp() {
        // we have to create a player manager object with null UI since we will be adding players manually
        playerManager = new PlayerManager(null);
        
       
        player1 = new Player("Player1", 1000);
        player2 = new Player("Player2", 2000);
        player3 = new Player("Player3", 3000);
        
        playerManager.getPlayers().add(player1);
        playerManager.getPlayers().add(player2);
        playerManager.getPlayers().add(player3);
    }
    
    /**
     * Tests that the current player should be the first one that was added
     */
    @Test
    public void testGetCurrentPlayer() {
        
        assertEquals(player1, playerManager.getCurrentPlayer());
    }
    
    /**
     * Tests the order and cycling of players. Testing player one at the end again to ensure the players loop around 
     */
    @Test
    public void testNextPlayer() {
        
        assertEquals(player1, playerManager.getCurrentPlayer());
        
        playerManager.nextPlayer();
        assertEquals(player2, playerManager.getCurrentPlayer());

        playerManager.nextPlayer();
        assertEquals(player3, playerManager.getCurrentPlayer());

        playerManager.nextPlayer();
        assertEquals(player1, playerManager.getCurrentPlayer());
    }
    
    /**
     * Tests the average resources without the specified player, for the  wasteland distribution system
     */
    @Test
    public void testGetAverageResourcesExcludingPlayer() {
        
        double avg = playerManager.getAverageResourcesExcludingPlayer(player1);
        assertEquals(2500.0, avg);
        
        
        avg = playerManager.getAverageResourcesExcludingPlayer(player2);
        assertEquals(2000.0, avg);
    }
    
    /**
     * Tests that the resources gets distributed evenly among players
     */
    @Test
    public void testDistributeResources() {

        Player distributer = player1; 
        int initialDistributerResources = distributer.getResources();
        int initialPlayer2Resources = player2.getResources();
        int initialPlayer3Resources = player3.getResources();
        
        // calculating the expected distribution 
        int distributionAmount = initialDistributerResources / 4; 
        int amountPerPlayer = distributionAmount / 2; 
        
        int removedAmount = initialDistributerResources / 4;
        distributer.removeResources(removedAmount, "Test distribution");
        
        player2.addResources(amountPerPlayer, "Test received");
        player3.addResources(amountPerPlayer, "Test received");
        
        
        assertEquals(initialDistributerResources - removedAmount, distributer.getResources());
        assertEquals(initialPlayer2Resources + amountPerPlayer, player2.getResources());
        assertEquals(initialPlayer3Resources + amountPerPlayer, player3.getResources());
    }

}
