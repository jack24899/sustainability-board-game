package sustainabilityBoardGame;

/**
 * Handles actions that occur when a player lands on a board square
 */
public class SquareActionHandler {

    /**
     * Method to handle actions for any type of square.
     * 
     * @param player 
     * @param square 
     * @param board 
     * @param playerManager 
     * @param ui 
     */
    public static void handleSquareAction(Player player, BoardSquare square, GameBoard board,
            PlayerManager playerManager, UserInterface ui) {
        if (square instanceof SpecialSquare) {
            handleSpecialSquare(player, (SpecialSquare) square, playerManager, ui);
        } else if (square instanceof PropertySquare) {
            handlePropertySquare(player, (PropertySquare) square, ui);
        }
    }

    /**
     * Handles actions when landing on a special square, only logic for wasteland
     * Go square effects are handled during movement in the main game loop
     * 
     * @param player 
     * @param square 
     * @param playerManager 
     * @param ui 
     */
    private static void handleSpecialSquare(Player player, SpecialSquare square, PlayerManager playerManager,
            UserInterface ui) {
        if (square.getType() == SpecialSquareType.WASTELAND) {
            System.out.println(player.getName() + " landed on Wasteland");

            // wasteland rules for wealthy players
            if (player.isEligibleForWastelandChoice()) {
                double avgResources = playerManager.getAverageResourcesExcludingPlayer(player);
                
                // If player has 50% more resources than average and hasn't recently had this choice
                if (player.getResources() > avgResources * 1.5 && !player.hasRecentlyHadWastelandChoice()) {
                    System.out.println("You have significantly more resources than others.");
                    System.out.println("Options:");
                    System.out.println("1. Stay in wasteland for next turn");
                    System.out.println("2. Distribute 25% of your resources to other players");

                    int choice = ui.getPlayerChoice(2);

                    if (choice == 1) {
                        player.sendToWasteland();
                        System.out.println(player.getName() + " will spend next turn in wasteland.");
                    } else {
                        // Distribute 25% of resources to other players as a wealth redistribution mechanism
                        playerManager.distributeResources(player);
                    }

                    // Flag to prevent this choice from being offered too frequently
                    player.setHasRecentlyHadWastelandChoice(true);
                } else {
                    System.out.println("Nothing happens this turn.");
                    player.setHasRecentlyHadWastelandChoice(false);
                }
            } else {
                System.out.println("Nothing happens this turn.");
            }
        }
    }

    /**
     * Handles actions when landing on a property square, including if a field is owned by that player or another, or not owned at all
     * 
     * @param player 
     * @param property 
     * @param ui 
     */
    private static void handlePropertySquare(Player player, PropertySquare property, UserInterface ui) {
        Player owner = property.getOwner();

        if (owner == null) {
            // Property is unowned - offer to purchase
            ui.offerToPurchase(player, property);
        } else if (owner != player) {
            // Property is owned by another player - pay rent
            payRent(player, property, ui);
        } else {
            System.out.println("You own this property.");
        }
    }

    /**
     * Handles rent payment when a player lands on another player's property. 
     * 
     * @param player 
     * @param property 
     * @param ui 
     */
    private static void payRent(Player player, PropertySquare property, UserInterface ui) {
        int rent = property.getRent();
        Player owner = property.getOwner();

        ui.displayPayRent(player, property, rent);

        if (player.getResources() >= rent) {
            // Full payment
            player.removeResources(rent, "Rent for " + property.getName());
            owner.addResources(rent, "Rent from " + player.getName());
            ui.displayRentPaid(player, owner, rent);
        } else {
            // Partial payment (bankruptcy case)
            ui.displayInsufficientResources(player);

            int remainingResources = player.getResources();
            player.removeResources(remainingResources, "Partial rent payment");
            owner.addResources(remainingResources, "Partial rent from " + player.getName());

            ui.displayPartialPayment(player, owner, remainingResources);
        }
    }
}
