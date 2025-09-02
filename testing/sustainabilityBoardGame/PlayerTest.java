package sustainabilityBoardGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	/**
	 * Tests player is properly initialized with the correct values, and that player is not in wasteland, or recently had the wasteland choice 
	 */
	@Test
    public void testPlayerInitialization() {
         
        Player player = new Player("Player1", 1000);
        
        assertEquals("Player1", player.getName());
        assertEquals(1000, player.getResources());
        assertEquals(0, player.getPosition());
        assertEquals(0, player.getTotalSpent());
        assertFalse(player.isInWasteland());
        assertFalse(player.hasRecentlyHadWastelandChoice());
    }
    
	/**
	 * Tests adding resources increases the players balance
	 */
    @Test
    public void testAddResources() {
        
        Player player = new Player("Player1", 1000);
        player.addResources(500, "Grant");
        
        assertEquals(1500, player.getResources());
    }
    
    /**
     * Tests removing resources decreases the balance and also increases the total spent
     */
    @Test
    public void testRemoveResources() {
        
        Player player = new Player("Player1", 1000);
        player.removeResources(300, "Rent");
        
        assertEquals(700, player.getResources());
        assertEquals(300, player.getTotalSpent());
    }
    
    /**
     * Tests wasteland mechanics
     */
    @Test
    public void testWastelandFunctions() {
        
        Player player = new Player("Player1", 1000);
        
        assertFalse(player.isInWasteland());
        
        player.sendToWasteland();
        assertTrue(player.isInWasteland());
        
        player.leaveWasteland();
        assertFalse(player.isInWasteland());
    }
    
    /**
     * Tests a players position can be updated
     */
    @Test
    public void testPosition() {
         
        Player player = new Player("Player1", 1000);
        assertEquals(0, player.getPosition());
        
        player.setPosition(5);
        assertEquals(5, player.getPosition());
    }
   
    /**
     * Tests wasteland choice boolean flags
     */
    @Test
    public void testWastelandChoiceFlags() {
         
        Player player = new Player("Player1", 1000);
        
        assertFalse(player.hasRecentlyHadWastelandChoice());
        
        player.setHasRecentlyHadWastelandChoice(true);
        assertTrue(player.hasRecentlyHadWastelandChoice());
    }
    
    /**
     * Tests that wasteland eligibility is based on having positive resources
     */
    @Test
    public void testEligibilityForWastelandChoice() {
         
        Player player = new Player("Player1", 100);
        assertTrue(player.isEligibleForWastelandChoice());
        
        player.removeResources(100, "Lost everything");
        assertFalse(player.isEligibleForWastelandChoice());
    }

}
