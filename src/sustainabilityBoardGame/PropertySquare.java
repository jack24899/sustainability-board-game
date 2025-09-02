package sustainabilityBoardGame;

/**
 * Represents a purchasable property square on the game board, fields can be owned by players and developed to increase their value and rent
 */
public class PropertySquare extends BoardSquare {
    
    private int purchaseCost;
    private PropertyType type;
    private Player owner;
    private int developmentLevel;
    private int totalInvestment;
    
    /**
     * Creates a new property square with the specified attributes
     * 
     * @param name 
     * @param purchaseCost 
     * @param type 
     */
    public PropertySquare(String name, int purchaseCost, PropertyType type) {
        super(name);
        this.purchaseCost = purchaseCost;
        this.type = type;
        
        // Initialize with default values (unowned and undeveloped)
        this.owner = null;
        this.developmentLevel = 0;
        this.totalInvestment = 0;
    }
    
    /**
     * Returns initial purchase cost of this field 
     * 
     * @return 
     */
    public int getPurchaseCost() {
        return purchaseCost;
    }
    
    /**
     * Returns type of this field
     * 
     * @return 
     */
    public PropertyType getType() {
        return type;
    }
    
    /**
     * Returns current owner of a field
     * 
     * @return 
     */
    public Player getOwner() {
        return owner;
    }
    
    /**
     * Sets a new owner for this field and updates total investment
     * 
     * @param owner 
     */
    public void setOwner(Player owner) {
        this.owner = owner;
        this.totalInvestment += purchaseCost; 
    }
    
    /**
     * Returns current development level of a property
     * 
     * @return 
     */
    public int getDevelopmentLevel() {
        return developmentLevel;
    }
    
    /**
     * Determines if a field can be further developed
     * 
     * @return 
     */
    public boolean canBeDeveloped() {
        int maxDevelopmentLevel;
        
        // Maximum development level depends on field type
        switch (type) {
            case WASTE_MANAGEMENT:
            case ECO_CITY:
                maxDevelopmentLevel = 2; 
                break;
            case MARINE_ENERGY:
            case LAND_ENERGY:
                maxDevelopmentLevel = 3; 
                break;
            default:
                maxDevelopmentLevel = 0; 
        }
        
        return developmentLevel < maxDevelopmentLevel;
    }
    
    /**
     * Calculates the cost to develop a field to the next level
     * 
     * @return 
     */
    public int getDevelopmentCost() {
        double multiplier;
        
        // Multiplier increases with each development level
        switch (developmentLevel) {
            case 0:
                multiplier = 1.5; 
                break;
            case 1:
                multiplier = 2.0; 
                break;
            case 2:
                // Energy properties have a further cost as they have a higher final development level
                multiplier = (type == PropertyType.MARINE_ENERGY ) ? 2.5 : 3.0;
                break;
            default:
                multiplier = 1.0; 
        }
        
        return (int)(purchaseCost * multiplier);
    }
    
    /**
     * Develops this property to the next level and updates the total investment
     * Should only be called after checking can be developed method and ensuring the player has enough resources
     */
    public void develop() {
        int cost = getDevelopmentCost();
        developmentLevel++;
        totalInvestment += cost; 
    }
    
    /**
     * Calculates the rent that should be paid when a player lands on a field
     * @return 
     */
    public int getRent() {
        double rentMultiplier;
        
        // Rent multiplier increases with development level
        switch (developmentLevel) {
            case 0:
                rentMultiplier = 0.2; 
                break;
            case 1:
                rentMultiplier = 0.5; 
                break;
            case 2:
                rentMultiplier = 1.0; 
                break;
            case 3:
                rentMultiplier = 1.5; 
                break;
            default:
                rentMultiplier = 0.1; 
        }
        
        // Special case: eco city at level 2 has higher rent
        if (type == PropertyType.ECO_CITY && developmentLevel == 2) {
            return (int)(purchaseCost * 2.0); 
        }
        
        return (int)(purchaseCost * rentMultiplier);
    }
    
    /**
     * Returns the total amount of resources invested in a field
     * 
     * @return 
     */
    public int getTotalInvestment() {
        return totalInvestment;
    }
    
    /**
     * Returns the name of the current development for a certain field
     * 
     * @return 
     */
    public String getCurrentDevelopmentName() {
        switch (type) {
            case WASTE_MANAGEMENT:
                switch (developmentLevel) {
                    case 0: return "Composting Plant";
                    case 1: return "Incineration Plant";
                    case 2: return "Advanced Waste Management";
                    default: return "Waste Management Plant";
                }
            case MARINE_ENERGY:
                switch (developmentLevel) {
                    case 0: return "Tidal Wave Plant";
                    case 1: return "Offshore Wind Farm";
                    case 2: return "Wave Power System";
                    case 3: return "Integrated Marine Energy Hub";
                    default: return "Marine Renewable Energy Plant";
                }
            case LAND_ENERGY:
                switch (developmentLevel) {
                    case 0: return "Solar Farm";
                    case 1: return "Biomass Energy Plant";
                    case 2: return "Nuclear Power Plant";
                    case 3: return "Smart Grid System";
                    default: return "Land Based Renewable Energy Plant";
                }
            case ECO_CITY:
                switch (developmentLevel) {
                    case 0: return "Eco Housing";
                    case 1: return "Sustainable Transport Network";
                    case 2: return "Fully Integrated Smart City";
                    default: return "Eco City";
                }
            default:
                return getName();
        }
    }
}
