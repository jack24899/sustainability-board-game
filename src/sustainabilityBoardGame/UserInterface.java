package sustainabilityBoardGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * UserInterface class manages all user interactions for the game
 */
public class UserInterface {
    private Scanner scanner; 
    
    /**
     * Constructor initializes the ui with a scanner for reading user input.
     * @param scanner 
     */
    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Displays the initial welcome message when the game starts
     */
    public void displayWelcomeMessage() {
        System.out.println("=== SAVE OUR PLANET: SUSTAINABILITY BOARD GAME ===");
    }
    /**
     * Displays how the efficiency score is calculated 
     */
    public void displayEfficiencyCalculation() {
    	System.out.println("\nEfficiency is a major mechanic in this game, so please take note of how it is calculated: \n(Total property value * 100) / (Total spent + total property value)\n");
    }
    
    /**
     * Prompts for and validates the number of players, ensuring a number is entered and valid number in range
     * 
     * @param min 
     * @param max 
     * @return 
     */
    public int getValidPlayerCount(int min, int max) {
        int numPlayers = 0;
        boolean validInput = false;
        
        // Loop until we get valid input within the range
        while (!validInput) {
            System.out.print("Enter number of players (" + min + "-" + max + "): ");
            try {
                numPlayers = Integer.parseInt(scanner.nextLine().trim());
                if (numPlayers >= min && numPlayers <= max) {
                    validInput = true; 
                } else {
                    System.out.println("Number of players must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                // Handle case where user enters text instead of a number
                System.out.println("Please enter a valid number.");
            }
        }
        
        return numPlayers;
    }
    
    /**
     * Collects unique names for each player, ensuring names are unique and not empty
     * 
     * @param numPlayers 
     * @return 
     */
    public List<String> getPlayerNames(int numPlayers) {
        System.out.println("\nEnter player names:");
        List<String> playerNames = new ArrayList<>();
        
        // Loop through each player position
        for (int i = 0; i < numPlayers; i++) {
            String name;
            do {
                System.out.print("Player " + (i + 1) + " name: ");
                name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty. Please try again.");
                } else if (playerNames.contains(name)) {
                    System.out.println("Name already taken. Please choose a unique name.");
                } else if(name.length() > 25) {
                	System.out.println("Please ensure you're name is less than 25 chars.");
                }
            } while (name.isEmpty() || playerNames.contains(name) || name.length() > 25); 
            
            playerNames.add(name);
        }
        
        return playerNames;
    }
    
    /**
     * Displays the current state of the game including board and player resources
     * 
     * @param board 
     * @param players 
     */
    public void displayGameStatus(GameBoard board, List<Player> players) {
        System.out.println("\n=== CURRENT GAME STATUS ===");
        displayBoard(board);
        
        // Display resource information for each player
        System.out.println("\nPlayer Resources:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getResources() + " resources");
        }
        System.out.println();
    }
    
    /**
     * Shows information about each square and its ownership status.
     * 
     * @param board 
     */
    public void displayBoard(GameBoard board) {
        System.out.println("\nCurrent Board State:");
        List<BoardSquare> squares = board.getAllSquares();
        
        // Loop through each square on the board
        for (int i = 0; i < squares.size(); i++) {
            BoardSquare square = squares.get(i);
            System.out.print("[" + i + "] " + square.getName());
            
            // Display additional information for property squares
            if (square instanceof PropertySquare) {
                PropertySquare property = (PropertySquare) square;
                Player owner = property.getOwner();
                if (owner != null) {
                    // Show owner and development level for owned properties
                    System.out.print(" - Owned by " + owner.getName());
                    if (!property.canBeDeveloped()) {
                    	System.out.print(" (Level " + property.getDevelopmentLevel() + ") - Max Level");
                    } else {
                    	System.out.print(" (Level " + property.getDevelopmentLevel() + ")");
                    }
                    
                } else {
                    System.out.print(" - Unowned");
                }
            }
            
            System.out.println();
        }
    }
    
    /**
     * Announces the beginning of a player's turn, and displays current resources available to player
     * 
     * @param player
     */
    public void displayPlayerTurn(Player player) {
        System.out.println("\n=== " + player.getName() + "'s TURN ===");
        System.out.println("Resources: " + player.getResources());
    }
    
    /**
     * Displays turn options and collects the players choice
     * 
     * @param canDevelopAny 
     * @return 
     */
    public int getPlayerTurnChoice(boolean canDevelopAny) {
        System.out.println("\nOptions:");
        System.out.println("1. Roll dice");
        
        // Only show property development option if the player can develop any properties
        if (canDevelopAny) {
            System.out.println("2. Develop a property");
        }
        
        // Adjust the quit option number based on available options
        System.out.println((canDevelopAny ? "3" : "2") + ". Quit game");
        
        return getPlayerChoice(canDevelopAny ? 3 : 2);
    }
    
    /**
     * General method to get a player's choice from a menu of options, validating user choice is in acceptable range
     * 
     * @param maxOption 
     * @return 
     */
    public int getPlayerChoice(int maxOption) {
        int choice = 0;
        boolean validInput = false;
        
        // Loop until valid input is received
        while (!validInput) {
            System.out.print("Enter your choice (1-" + maxOption + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= maxOption) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a number between 1 and " + maxOption + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        
        return choice;
    }
    
    /**
     * Displays the result of a player's dice roll
     * 
     * @param player 
     * @param diceRoll 
     */
    public void displayDiceRoll(Player player, DiceRoll diceRoll) {
        System.out.println(player.getName() + " rolled " + diceRoll.getDice1() + " and " + diceRoll.getDice2() + 
                           " for a total of " + diceRoll.getTotal());
    }
    
    /**
     * Announces a players movement to a new square
     * 
     * @param player The player who moved
     * @param square The destination square
     */
    public void displayPlayerMovement(Player player, BoardSquare square) {
        System.out.println(player.getName() + " moved to " + square.getName());
    }
    
    /**
     * Displays information when a player passes the go square and collects resources, shows the player's efficiency score which affects the grant amount
     * 
     * @param player 
     * @param efficiency 
     * @param grantAmount 
     */
    public void displayPassedGo(Player player, int efficiency, int grantAmount) {
        System.out.println(player.getName() + " passed Sustainability Grant!");
        System.out.println("Efficiency score: " + efficiency);
        System.out.println("Grant received: " + grantAmount + " resources");
    }
    
    /**
     * Displays a message when a player must skip their turn due to being in the wasteland.
     * 
     * @param player 
     */
    public void displayWastelandSkipMessage(Player player) {
        System.out.println("\n" + player.getName() + " is in wasteland and misses this turn.");
    }
    
    /**
     * Announces when a player decides to quit the game
     * 
     * @param player 
     */
    public void displayQuitMessage(Player player) {
        System.out.println(player.getName() + " has decided to quit the game.");
    }
    
    /**
     * Displays a message when the game ends due to only one player remaining
     */
    public void displayGameOver() {
        System.out.println("\nOnly one player remains with resources. Game over!");
    }
    
    /**
     * Displays the final results of the game, with each players performance
     * 
     * @param players 
     * @param board 
     */
    public void displayFinalResults(List<Player> players, GameBoard board) {
        System.out.println("\n=== FINAL RESULTS ===");
        
        // Loop through each player to display their final status
        for (Player player : players) {
            int totalWorth = player.getResources();
            int efficiencyScore = EfficiencyCalculator.calculateEfficiencyScore(player, board);
            
            System.out.println("\n" + player.getName() + ":");
            System.out.println("Resources: " + player.getResources());
            System.out.println("Efficiency Score: " + efficiencyScore);
            
            // Display properties owned by the player
            System.out.println("Properties owned:");
            List<PropertySquare> properties = board.getPlayerProperties(player);
            
            if (properties.isEmpty()) {
                System.out.println("None");
            } else {
                for (PropertySquare property : properties) {
                    System.out.println("- " + property.getName() + " (Level " + property.getDevelopmentLevel() + 
                                     ", Value: " + property.getTotalInvestment() + ")");
                    totalWorth += property.getTotalInvestment();
                }
            }
            
            // Display total worth (resources + property investments)
            System.out.println("Total Worth: " + totalWorth);
        }
        
        System.out.println("\nThank you for playing Save Our Planet!");
    }
    
    /**
     * Announces when a player is distributing resources (for wasteland)
     * 
     * @param player 
     * @param amount 
     */
    public void displayResourceDistribution(Player player, int amount) {
        System.out.println(player.getName() + " is distributing " + amount + " resources");
    }
    
    /**
     * Announces when a player receives resources
     * 
     * @param player 
     * @param amount 
     */
    public void displayResourceReceived(Player player, int amount) {
        System.out.println(player.getName() + " received " + amount + " resources");
    }
    
    /**
     * Handles the process of offering a property for purchase, displays property information and prompts player for a decision
     * 
     * @param player
     * @param property 
     */
    public void offerToPurchase(Player player, PropertySquare property) {
        System.out.println("This property is unowned.");
        System.out.println("Cost to purchase: " + property.getPurchaseCost() + " resources");
        
        // Check if player has enough resources to purchase
        if (player.getResources() < property.getPurchaseCost()) {
            System.out.println("You don't have enough resources to purchase this property.");
            return;
        }
        
        System.out.println("Would you like to purchase it?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        
        int choice = getPlayerChoice(2);
        
        
        if (choice == 1) {
            // Player decides to purchase the property
            player.removeResources(property.getPurchaseCost(), "Purchase of " + property.getName());
            property.setOwner(player);
            System.out.println(player.getName() + " purchased " + property.getName() + " for " + property.getPurchaseCost() + " resources");
        } else {
            // Player decides not to purchase
            System.out.println(player.getName() + " decided not to purchase " + property.getName());
        }
    }
    
    /**
     * Displays information about rent that needs to be paid, shows owner and total rent due
     * 
     * @param player 
     * @param property 
     * @param amount 
     */
    public void displayPayRent(Player player, PropertySquare property, int amount) {
        Player owner = property.getOwner();
        System.out.println(property.getName() + " is owned by " + owner.getName());
        System.out.println("Rent to pay: " + amount + " resources");
    }
    
    /**
     * Announces when a player pays rent to another player
     * 
     * @param player 
     * @param owner 
     * @param amount 
     */
    public void displayRentPaid(Player player, Player owner, int amount) {
        System.out.println(player.getName() + " paid " + amount + " resources to " + owner.getName());
    }
    
    /**
     * Displays a message when a player doesn't have enough resources to pay rent
     * @param player 
     */
    public void displayInsufficientResources(Player player) {
        System.out.println(player.getName() + " doesn't have enough resources to pay rent!");
    }
    
    /**
     * Displays a message when a player makes a partial payment and is bankrupt
     * 
     * @param player 
     * @param owner 
     * @param amount 
     */
    public void displayPartialPayment(Player player, Player owner, int amount) {
        System.out.println("All remaining resources will be transferred to " + owner.getName());
        System.out.println(player.getName() + " has run out of resources!");
    }
}
