package sustainabilityBoardGame;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents the game board with all its squares, provides method to interact with squares, and tracks property ownership
 */
public class GameBoard {
    
    private List<BoardSquare> squares;
    
    /**
     * Constructor that creates a new game board and initializes all squares
     */
    public GameBoard() {
        this.squares = new ArrayList<>();
        initializeBoard();
    }
    
    /**
     * Initializes the board with all required squares in the correct order
     */
    private void initializeBoard() {
        // Create the board with fields in order
        squares.add(new SpecialSquare("Sustainability Grant", SpecialSquareType.GO));
        squares.add(new PropertySquare("Waste Management Plant", 200, PropertyType.WASTE_MANAGEMENT));
        squares.add(new PropertySquare("Marine Renewable Energy Plant", 300, PropertyType.MARINE_ENERGY));
        squares.add(new SpecialSquare("Wasteland", SpecialSquareType.WASTELAND));
        squares.add(new PropertySquare("Land Based Renewable Energy Plant", 350, PropertyType.LAND_ENERGY));
        squares.add(new PropertySquare("Eco City", 400, PropertyType.ECO_CITY));
    }
    
    /**
     * Gets the square at the specified position on the board
     * @param  position
     * @return 
     */
    public BoardSquare getSquareAt(int position) {
    	if (position < 0 || position >= squares.size()) {
            // Handle invalid position
            return null; // Or throw exception
        }
        return squares.get(position);
    }
    
    /**
     * Gets a list of all squares on the board
     * @return 
     */
    public List<BoardSquare> getAllSquares() {
        return squares;
    }
    
    /**
     * Gets a list of all property squares owned by a specific player.
     * @param player 
     * @return 
     */
    public List<PropertySquare> getPlayerProperties(Player player) {
        List<PropertySquare> properties = new ArrayList<>();
        
        // Iterate through all squares to find properties owned by the player
        for (BoardSquare square : squares) {
            if (square instanceof PropertySquare) {
                PropertySquare property = (PropertySquare) square;
                if (property.getOwner() == player) {
                    properties.add(property);
                }
            }
        }
        
        return properties;
    }

}