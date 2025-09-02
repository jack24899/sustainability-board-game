package sustainabilityBoardGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EfficiencyCalculatorTest {

	private GameBoard gameBoard;
    private Player player;
    
    @BeforeEach
    public void setUp() {
        gameBoard = new GameBoard();
        player = new Player("TestPlayer", 1000);
    }
    
    /**
     * Tests for when an a player has no investments, which means the score should be zero 
     */
    @Test
    public void testEfficiencyScore_NoInvestments() {
        
        int efficiency = EfficiencyCalculator.calculateEfficiencyScore(player, gameBoard);
        assertEquals(0, efficiency);
    }
    
    /**
     * Tests for a players efficiency score (when a property is owned) based of an expected score calculated using the efficiency formula 
     */
    @Test
    public void testEfficiencyScore_WithInvestments() {
        
        PropertySquare property = (PropertySquare) gameBoard.getSquareAt(1); 
        int purchaseCost = property.getPurchaseCost(); 
        
        
        player.removeResources(purchaseCost, "Property purchase");
        property.setOwner(player);
        
        int expectedEfficiency = (1000 * 100) / (purchaseCost + 1000);
        int actualEfficiency = EfficiencyCalculator.calculateEfficiencyScore(player, gameBoard);
        assertEquals(expectedEfficiency, actualEfficiency);
    }
    
    /**
     * Tests for a players efficiency score (when a property is owned AND developed) 
     * based of an expected score calculated using the efficiency formula 
     */
    @Test
    public void testEfficiencyScore_WithDevelopment() {
        
        PropertySquare property = (PropertySquare) gameBoard.getSquareAt(1); 
        int purchaseCost = property.getPurchaseCost(); 

        player.removeResources(purchaseCost, "Property purchase");
        property.setOwner(player);
 
        int developmentCost = property.getDevelopmentCost(); 
        player.removeResources(developmentCost, "Property development");
        property.develop();
        
        int totalSpent = purchaseCost + developmentCost;
        int expectedEfficiency = (1000 * 100) / (totalSpent + 1000);
        
        int actualEfficiency = EfficiencyCalculator.calculateEfficiencyScore(player, gameBoard);
        assertEquals(expectedEfficiency, actualEfficiency);
    }
	

}
