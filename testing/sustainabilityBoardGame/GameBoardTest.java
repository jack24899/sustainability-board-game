package sustainabilityBoardGame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBoardTest {

	private GameBoard gameBoard;
    private Player player1;
    
    @BeforeEach
    public void setUp() {
       this.gameBoard = new GameBoard();
       this.player1 = new Player("Player1", 1000);
    }
    
    /**
     * Tests the board initalization set up method 
     */
    @Test
    public void testBoardInitialization() {
        
        assertEquals(6, gameBoard.getAllSquares().size());
        
        // Verify first square is the go square and that the last square is a property
        BoardSquare firstSquare = gameBoard.getSquareAt(0);
        BoardSquare lastSquare = gameBoard.getSquareAt(5);
        
        assertTrue(firstSquare instanceof SpecialSquare);
        assertEquals("Sustainability Grant", firstSquare.getName());
        
        assertTrue(lastSquare instanceof PropertySquare);
        assertEquals("Eco City", lastSquare.getName());
    }
    
    /**
     * Tests getting a special square and a property square 
     */
    @Test
    public void testGetSquareAt() {
        
        BoardSquare specialSquare = gameBoard.getSquareAt(3);
        assertTrue(specialSquare instanceof SpecialSquare);
        assertEquals("Wasteland", specialSquare.getName());

        BoardSquare propertySquare = gameBoard.getSquareAt(1);
        assertTrue(propertySquare instanceof PropertySquare);
        assertEquals("Waste Management Plant", propertySquare.getName());
        assertEquals(PropertyType.WASTE_MANAGEMENT, ((PropertySquare)propertySquare).getType());
    }
    
    /**
     * Tests getting a players properties. Initally tests that a player has no properties, 
     * then a player is assigned a property, which is verified 
     */
    @Test
    public void testGetPlayerProperties() {
        
        List<PropertySquare> properties = gameBoard.getPlayerProperties(player1);
        assertTrue(properties.isEmpty());
        
        PropertySquare property = (PropertySquare) gameBoard.getSquareAt(1);
        property.setOwner(player1);
        
        properties = gameBoard.getPlayerProperties(player1);
        assertEquals(1, properties.size());
        assertEquals("Waste Management Plant", properties.get(0).getName());
    }
    


}
