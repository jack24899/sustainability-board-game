package sustainabilityBoardGame;

import java.util.List;

/**
 * Utility class to see if any properties in list can be developed
 */
public class PropertyUtils {

    /**
     * Checks if any of the properties in the provided list can be developed further, ie if its current development level is below the maximum
     * @param properties 
     * @return 
     */
    public static boolean canDevelopAny(List<PropertySquare> properties) {
        for (PropertySquare property : properties) {
            if (property.canBeDeveloped()) {
                return true;
            }
        }
        return false;
    }
}
