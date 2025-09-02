package sustainabilityBoardGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PropertySquareTest {


	/**
	 * Tests property is properly initialized with the correct values
	 */
    @Test
    public void testPropertySquareInitialization() {
         
        PropertySquare property = new PropertySquare("property name test", 200, PropertyType.LAND_ENERGY);
        
        assertEquals("property name test", property.getName());
        assertEquals(200, property.getPurchaseCost());
        assertEquals(PropertyType.LAND_ENERGY, property.getType());
        assertNull(property.getOwner());
        assertEquals(0, property.getDevelopmentLevel());
        assertEquals(0, property.getTotalInvestment());
    }
    
    /**
     * Tests if setting an owner updates owner and total investment value
     */
    @Test
    public void testSetOwner() {
       
        PropertySquare property = new PropertySquare("property test", 200, PropertyType.LAND_ENERGY);
        Player player = new Player("Test player", 1000);
        
        property.setOwner(player);
        
        assertEquals(player, property.getOwner());
        assertEquals(200, property.getTotalInvestment());
    }
    
    /**
     * Tests the development level limits for different property types. Only one test for each development level, 2 and 3.
     */
    @Test
    public void testDevelopmentLevels() {
       
        PropertySquare wasteProperty = new PropertySquare("property name", 150, PropertyType.WASTE_MANAGEMENT);
        PropertySquare marineProperty = new PropertySquare("property name", 250, PropertyType.MARINE_ENERGY);
        
        //tests that waste management can develop 2 levels 
        assertTrue(wasteProperty.canBeDeveloped());
        wasteProperty.develop(); 
        assertTrue(wasteProperty.canBeDeveloped());
        wasteProperty.develop(); 
        assertFalse(wasteProperty.canBeDeveloped());
        
        //tests marine energy can develop 3 levels 
        assertTrue(marineProperty.canBeDeveloped());
        marineProperty.develop(); 
        assertTrue(marineProperty.canBeDeveloped());
        marineProperty.develop(); 
        assertTrue(marineProperty.canBeDeveloped());
        marineProperty.develop(); 
        assertFalse(marineProperty.canBeDeveloped());
    }
    
    /**
     * Tests that development costs are different at different levels
     */
    @Test
    public void testDevelopmentCost() {
        
        PropertySquare property = new PropertySquare("property name", 200, PropertyType.LAND_ENERGY);
        
        // Initial development cost should be 1.5 * purchase cost
        assertEquals(300, property.getDevelopmentCost());
        
       
        property.develop();
        // Second development cost should be 2 * purchase cost
        assertEquals(400, property.getDevelopmentCost());
        
        
        property.develop();
        // Third development cost should be 3.0  * purchase cost for land energy field
        assertEquals(600, property.getDevelopmentCost());
    }
    
    /**
     * Tests that rent increases at different development levels
     */
    @Test
    public void testRentCalculation() {
        
        PropertySquare property = new PropertySquare("property name", 200, PropertyType.LAND_ENERGY);
        
        // level 0 should have a rent of 0.2 * purchase cost
        assertEquals(40, property.getRent());
        
        // level 1 should have a rent of 0.5 * purchase cost
        property.develop();
        assertEquals(100, property.getRent());
        
        // level 2 should have a rent of 1.0 * purchase cost
        property.develop();
        assertEquals(200, property.getRent());
        
        // level 3 should have a rent of 1.5 * purchase cost
        property.develop();
        assertEquals(300, property.getRent());
    }
    
    /**
     * Tests special rent calculation for Eco City at level 2
     */
    @Test
    public void testEcoCitySpecialRent() {
        
        PropertySquare ecoCity = new PropertySquare("Eco city", 300, PropertyType.ECO_CITY);
        
        
        ecoCity.develop(); 
        ecoCity.develop(); 
        
        // Eco city at level 2 should have a higher rent =  2.0 * purchase cost
        assertEquals(600, ecoCity.getRent());
    }
    
    /**
     * Tests total investment tracking after purchase and developments
     */
    @Test
    public void testTotalInvestment() {
         
        PropertySquare property = new PropertySquare("property name", 200, PropertyType.LAND_ENERGY);
        Player player = new Player("Test player", 1000);
        
        // Set owner which should also add purchase cost to total investment 
        property.setOwner(player);
        assertEquals(200, property.getTotalInvestment());
        
        // Checking again for an increase in total investment, with 300 being development cost 
        property.develop();
        assertEquals(500, property.getTotalInvestment());
        
        // Checking again for an increase in total investment, with 400 being development cost 
        property.develop();
        assertEquals(900, property.getTotalInvestment());
        
        // Checking again for an increase in total investment, with 600 being development cost 
        property.develop();
        assertEquals(1500, property.getTotalInvestment());
    }
    
    /**
     * Tests development names for different property types and levels
     */
    @Test
    public void testDevelopmentNames() {
        
        PropertySquare landEnergy = new PropertySquare("Energy Field", 200, PropertyType.LAND_ENERGY);
        
        assertEquals("Solar Farm", landEnergy.getCurrentDevelopmentName());
        
        landEnergy.develop();
        assertEquals("Biomass Energy Plant", landEnergy.getCurrentDevelopmentName());
        
        landEnergy.develop();
        assertEquals("Nuclear Power Plant", landEnergy.getCurrentDevelopmentName());
        
        landEnergy.develop();
        assertEquals("Smart Grid System", landEnergy.getCurrentDevelopmentName());
    }

}
