package sustainabilityBoardGame;

import java.util.Random;

/**
 * Represents a roll of two dice in the game
 * Creates a random object to represent the random roll of a die
 */
public class DiceRoll {
    
    private int dice1;
    private int dice2;
    
    // Static random instance for generating dice values as using a static is more efficient than creating a new one for each roll 
    private static final Random random = new Random();

    /**
     * Constructor which acts as rolling the dice
     */
    public DiceRoll() {
        // Generate random values between 1-6 for both dice
        this.dice1 = random.nextInt(6) + 1;
        this.dice2 = random.nextInt(6) + 1;
    }

    /**
     * Gets the value of the first die for outputting result to user
     * 
     * @return 
     */
    public int getDice1() {
        return dice1;
    }

    /**
     * Gets the value of the second die to output the result to user
     * @return 
     */
    public int getDice2() {
        return dice2;
    }

    /**
     * Gets the total value of both dice combined
     * @return 
     */
    public int getTotal() {
        return dice1 + dice2;
    }
}