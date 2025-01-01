package models;

import java.util.Random;

public class EnhancedDice {
    private int currentValue;
    private static final int MIN_VALUE = -3;
    private static final int MAX_VALUE = 6;
    private Random random;

    public EnhancedDice() {
        random = new Random();
    }

    // Roll the dice to get a random value between MIN_VALUE and MAX_VALUE
    public int roll() {
        currentValue = random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
        return currentValue;
    }

    // Get the current value of the dice
    public int getCurrentValue() {
        return currentValue;
    }
}
