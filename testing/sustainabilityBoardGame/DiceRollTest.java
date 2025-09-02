package sustainabilityBoardGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiceRollTest {

	/**
	 * Tests that dice values are within the expected range (1-6). This test is ran ten times to ensure that a number will not occur outside the range 
	 */
	@Test
    public void testDiceValuesInRange() {
        
        for (int i = 0; i < 10; i++) {
            DiceRoll diceRoll = new DiceRoll();
            
            int dice1Value = diceRoll.getDice1();
            int dice2Value = diceRoll.getDice2();
            
            assertTrue(dice1Value >= 1 && dice1Value <= 6);
            assertTrue(dice2Value >= 1 && dice2Value <= 6);
        }
    }
    
	/**
	 * Tests that the total is correctly calculated as the sum of both dice
	 */
    @Test
    public void testTotalIsSum() {
        
        DiceRoll diceRoll = new DiceRoll();
        int expected = diceRoll.getDice1() + diceRoll.getDice2();
        assertEquals(expected, diceRoll.getTotal());
    }

}
