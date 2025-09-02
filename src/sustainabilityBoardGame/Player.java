package sustainabilityBoardGame;

/**
 * Represents a player in the game, managing resources, position etc.
 */
public class Player {
    
    private String name;
    
    private int resources;      
    private int totalSpent;     
    
    
    private int position;       
    private boolean inWasteland; 
    
    // This will check if a player has recently has the wasteland choice, assuring this mechanic does not get repeated
    private boolean hasRecentlyHadWastelandChoice; 

    /**
     * Creates a new player with initial resources and default state
     * @param name 
     * @param initialResources 
     */
    public Player(String name, int initialResources) {
        this.name = name;
        this.resources = initialResources;
        this.position = 0;          
        this.totalSpent = 0;
        this.inWasteland = false;
        this.hasRecentlyHadWastelandChoice = false;
    }

    /**
     * Gets the player's name
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's current resource amount
     * @return 
     */
    public int getResources() {
        return resources;
    }

    /**
     * Adds resources to the player's balance and outputs transaction
     * @param amount 
     * @param reason 
     */
    public void addResources(int amount, String reason) {
        resources += amount;
        System.out.println(name + " received " + amount + " resources (" + reason + ")");
        System.out.println("New balance: " + resources);
    }

    /**
     * Removes resources from the player's balance and outputs transaction
     * @param amount 
     * @param reason 
     */
    public void removeResources(int amount, String reason) {
        resources -= amount;
        totalSpent += amount;
        System.out.println(name + " spent " + amount + " resources (" + reason + ")");
        System.out.println("New balance: " + resources);
    }

    /**
     * Gets the player's current position
     * @return 
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the player's current position 
     * @param position 
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets the total amount of resources spent which is used for efficiency score
     * @return Total resources spent
     */
    public int getTotalSpent() {
        return totalSpent;
    }

    /**
     * Checks if the player is currently in the wasteland
     * @return 
     */
    public boolean isInWasteland() {
        return inWasteland;
    }

    /**
     * Sends the player to the wasteland, which will result in spending next turn here
     */
    public void sendToWasteland() {
        inWasteland = true;
    }

    /**
     * Releases the player from the wasteland
     */
    public void leaveWasteland() {
        inWasteland = false;
    }

    /**
     * Checks if the player recently had a wasteland choice, to prevent repeatedly being in wasteland
     * @return 
     */
    public boolean hasRecentlyHadWastelandChoice() {
        return hasRecentlyHadWastelandChoice;
    }

    /**
     * Sets the flag indicating if the player recently had a wasteland choice
     * @param 
     */
    public void setHasRecentlyHadWastelandChoice(boolean value) {
        hasRecentlyHadWastelandChoice = value;
    }

    /**
     * Determines if the player is eligible for a wasteland choice, must have positive resources
     * @return 
     */
    public boolean isEligibleForWastelandChoice() {
        return resources > 0;
    }
}
