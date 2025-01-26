package models;

public abstract class AbstractTemplatePlayerTurn {

    // Template method defining the skeleton of a player’s turn
    public void playTurn() {
        rollDice();            // Step 1: Roll the dice
        makeMove();            // Step 2: Make a move based on the roll
        applySpecialRules();   // Step 3: Apply special rules (like answering questions)
        endTurn();             // Step 4: End the turn
    }

    // These methods can be abstract or with default implementation
    protected abstract void rollDice();
    protected abstract void makeMove();
    protected abstract void applySpecialRules();
    protected abstract void endTurn();
}

