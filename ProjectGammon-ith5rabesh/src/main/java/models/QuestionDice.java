package models;

import java.util.Random;

public class QuestionDice {
    private static final int MIN_VALUE = 1; // Minimum dice value
    private static final int MAX_VALUE = 3; // Maximum dice value
    private int currentValue;
    private Random random;

    public QuestionDice() {
        random = new Random();
    }

    // Roll the dice
    public int roll() {
        currentValue = random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
        return currentValue;
    }

    // Get the current value of the dice
    public int getCurrentValue() {
        return currentValue;
    }

    // Always triggers a question because it’s a dedicated Question Dice
    public boolean triggersQuestion() {
        return true;
    }
}
