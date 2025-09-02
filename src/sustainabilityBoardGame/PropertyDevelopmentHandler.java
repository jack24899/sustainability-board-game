package sustainabilityBoardGame;

import java.util.List;

/**
 * Handles the property development logic in the game
 * This class provides methods to manage the development of properties owned by players
 */
public class PropertyDevelopmentHandler {

    /**
     * Displays available properties for development, processes the player's choice, checks resource requirements, and handles special cases like ECO_CITY development.
     * @param player 
     * @param developableProperties 
     * @param ui 
     */
    public static void developProperty(Player player, List<PropertySquare> developableProperties, UserInterface ui) {
        System.out.println("\nSelect a property to develop:");

        // Display all properties that can be developed
        for (int i = 0; i < developableProperties.size(); i++) {
            PropertySquare property = developableProperties.get(i);
            System.out.println((i + 1) + ". " + property.getName() + " (Current Level: " + property.getDevelopmentLevel() +
                ", Development Cost: " + property.getDevelopmentCost() + ")");
        }

        // Get player's choice of which property to develop
        int choice = ui.getPlayerChoice(developableProperties.size());
        PropertySquare propertyToDevelop = developableProperties.get(choice - 1);

        int cost = propertyToDevelop.getDevelopmentCost();
        if (player.getResources() >= cost) {
            // Special case for Eco City final development, spending a turn in the wasteland
            if (propertyToDevelop.getType() == PropertyType.ECO_CITY &&
                propertyToDevelop.getDevelopmentLevel() == 1) {
                System.out.println("This development requires spending the next turn in the wasteland.");
                System.out.println("Do you want to proceed?");
                System.out.println("1. Yes");
                System.out.println("2. No");

                int confirm = ui.getPlayerChoice(2);
                if (confirm == 2) {
                    System.out.println("Development cancelled.");
                    return;
                }

                // Send player to wasteland as penalty for ECO_CITY final development
                player.sendToWasteland();
            }

            // Deduct resources and develop the property
            player.removeResources(cost, "Development of " + propertyToDevelop.getName());
            propertyToDevelop.develop();

            System.out.println(player.getName() + " developed " + propertyToDevelop.getName() +
                " to level " + propertyToDevelop.getDevelopmentLevel() +
                " for " + cost + " resources");
        } else {
            System.out.println("Not enough resources to develop this property.");
        }
    }
}
