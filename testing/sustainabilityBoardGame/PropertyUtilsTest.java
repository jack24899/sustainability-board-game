package sustainabilityBoardGame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PropertyUtilsTest {

	/**
	 * Tests for if a player can develop no properties 
	 */
	@Test
	public void testCanDevelopAny_ReturnsFalse() {
		
		List<PropertySquare> properties = new ArrayList<>();

		//testing an empty list here 
		assertFalse(PropertyUtils.canDevelopAny(properties));

		// creating a property at max development 
		PropertySquare property = new PropertySquare("Test property", 200, PropertyType.WASTE_MANAGEMENT);
		property.develop(); 
		property.develop(); 
		properties.add(property);

		assertFalse(PropertyUtils.canDevelopAny(properties));
	}

	/**
	 * Tests for if a player can develop a property 
	 */
	@Test
	public void testCanDevelopAny_ReturnsTrue() {
		
		List<PropertySquare> properties = new ArrayList<>();
		PropertySquare developableProperty = new PropertySquare("Developable Property", 300, PropertyType.MARINE_ENERGY);
		properties.add(developableProperty);

		assertTrue(PropertyUtils.canDevelopAny(properties));
	}

}
