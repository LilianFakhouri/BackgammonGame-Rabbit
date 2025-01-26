package controller;



import models.EnhancedDice;
import models.QuestionDice;

public class DiceFactory {

    /**
     * Creates dice objects based on the game level.
     *
     * @param level The selected game level ("Medium" or "Hard").
     * @return The corresponding dice object or null if the level doesn't match.
     */
    public static Object createDice(String level) {
        switch (level) {
            case "Medium":
                return new QuestionDice(); // Return a QuestionDice for "Medium" level
            case "Hard":
                return new EnhancedDice(); // Return an EnhancedDice for "Hard" level
            default:
                return null; // Return null for unsupported levels
        }
    }
}
