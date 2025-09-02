package sustainabilityBoardGame;

import java.util.List;
import java.util.Scanner;

/**
 * Main class for the sustainability board game, serving as the primary controller for the game
 */
public class SustainabilityBoardGame {
    
    private GameBoard board;          
    private PlayerManager playerManager; 
    private GameState gameState;       
    private UserInterface ui;          
    private Scanner scanner;           
    
    /**
     * Application entry point, starts a new game
     * 
     * @param args 
     */
    public static void main(String[] args) {
        SustainabilityBoardGame game = new SustainabilityBoardGame();
        game.initialize();
        game.play();
    }
    
    /**
     * Sets up the scanner for user input and initializes the user interface
     */
    public SustainabilityBoardGame() {
        this.scanner = new Scanner(System.in);
        this.ui = new UserInterface(scanner);
    }
    
    /**
     * Initializes all game components before starting play
     */
    private void initialize() {
        ui.displayWelcomeMessage();
        ui.displayEfficiencyCalculation();
        
        // Create core game components
        board = new GameBoard();
        playerManager = new PlayerManager(ui);
        gameState = new GameState();
        
        // Set up players with their starting resources defined in GameConfig
        playerManager.initializePlayers(GameConfig.INITIAL_RESOURCES);
        gameState.setGameRunning(true);
    }
    
    /**
     * Main game loop
     */
    private void play() {
        while (gameState.isGameRunning()) {
            Player currentPlayer = playerManager.getCurrentPlayer();
            
            // Skip player's turn if they are in the wasteland 
            if (currentPlayer.isInWasteland()) {
                ui.displayWastelandSkipMessage(currentPlayer);
                currentPlayer.leaveWasteland();  
                playerManager.nextPlayer();
                continue;  
            }
            
            // Display current game status before player's turn
            ui.displayGameStatus(board, playerManager.getPlayers());
            
            // Player chooses quit
            if (!handlePlayerTurn(currentPlayer)) {
                break; 
            }
            
            // Check if game should end after this turn
            checkGameEndConditions();
            
            // Move to next player if game is still running
            if (gameState.isGameRunning()) {
                playerManager.nextPlayer();
            }
        }
        
        // Game has ended, display final results and statistics
        ui.displayFinalResults(playerManager.getPlayers(), board);
    }
    
    /**
     * Handles a single player's turn, presenting options to the player and processing choice.
     * 
     * @param player
     * @return boolean 
     */
    private boolean handlePlayerTurn(Player player) {
        ui.displayPlayerTurn(player);
        
        // Get properties owned by the current player
        List<PropertySquare> ownedProperties = board.getPlayerProperties(player);
        
        // Check if the player can develop any of their properties
        boolean canDevelopAny = PropertyUtils.canDevelopAny(ownedProperties);
        
        // Get the player's choice for this turn
        int choice = ui.getPlayerTurnChoice(canDevelopAny);
        
        // Process player's choice
        switch (choice) {
            case 1:
                // Roll dice and move player
                handleDiceRollAndMove(player);
                return true;
            case 2:
                if (canDevelopAny) {
                    // Develop a property if player has developable properties
                    PropertyDevelopmentHandler.developProperty(player, ownedProperties, ui);
                } else {
                    // Option 2 is quit if player has no developable properties
                    ui.displayQuitMessage(player);
                    gameState.setGameRunning(false);
                }
                return true;
            case 3:
                if (canDevelopAny) {
                    // Option 3 is quit if player has developable properties
                    ui.displayQuitMessage(player);
                    gameState.setGameRunning(false);
                }
                return true;
            default:
                return true;  // Continue game for any other input
        }
    }
    
    /**
     * Handles the dice roll, player movement
     * 
     * @param player 
     */
    private void handleDiceRollAndMove(Player player) {
        int grantAmount;
        // Roll dice and display result
        DiceRoll diceRoll = new DiceRoll();
        ui.displayDiceRoll(player, diceRoll);
        
        // Calculate new position
        int oldPosition = player.getPosition();
        int newPosition = (oldPosition + diceRoll.getTotal()) % GameConfig.TOTAL_SQUARES;
        
        // Check if player passes go
        if (newPosition < oldPosition ) {
            // Calculate efficiency score for bonus grant calculation
            int efficiency = EfficiencyCalculator.calculateEfficiencyScore(player, board);
            
            
            if (efficiency == 0) {
            	//base grant amount if player has not purchased and fields
                grantAmount = 100; 
            } else {
                // Grant amount increases with player's efficiency score
                grantAmount = 100 + (efficiency * 5); 
            }
            
            // Display message and add resources to player
            ui.displayPassedGo(player, efficiency, grantAmount);
            player.addResources(grantAmount, "Sustainability Grant");
        }
        
        // Update player position and notify
        player.setPosition(newPosition);
        ui.displayPlayerMovement(player, board.getSquareAt(newPosition));
        
        // Process actions for the square the player landed on
        SquareActionHandler.handleSquareAction(player, board.getSquareAt(newPosition), board, playerManager, ui);
    }
    
    /**
     * Checks if any game ending conditions have been met
     */
    private void checkGameEndConditions() {
        int playersWithResources = 0;
        
        // Count players who still have resources
        for (Player player : playerManager.getPlayers()) {
            if (player.getResources() > 0) {
                playersWithResources++;
            }
        }
        
        // End game if only one or zero players have resources left
        if (playersWithResources <= 1) {
            ui.displayGameOver();
            gameState.setGameRunning(false);
        }
    }
}
