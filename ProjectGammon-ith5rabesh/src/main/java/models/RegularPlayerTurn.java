package models;

public class RegularPlayerTurn extends AbstractTemplatePlayerTurn {

    @Override
    protected void rollDice() {
        System.out.println("Rolling two regular dice...");
        // Implement the logic for rolling dice here (e.g., random number generation)
    }

    @Override
    protected void makeMove() {
        System.out.println("Making a move based on dice rolls...");
        // Implement the logic for making a move (e.g., choosing a piece to move)
    }

    @Override
    protected void applySpecialRules() {
        System.out.println("Checking for special rules...");
        // Implement logic for handling any special rules such as questions or surprises
    }

    @Override
    protected void endTurn() {
        System.out.println("Ending the player's turn...");
        // Implement the logic for ending the turn (e.g., switching to the next player)
    }
}
