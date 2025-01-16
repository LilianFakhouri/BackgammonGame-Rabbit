package GUI;

import javax.swing.JPanel;
import models.QuestionDice;

public class questionDiceGui extends JPanel {
    private QuestionDice questionDice;

    public questionDiceGui() {
        // יצירת QuestionDice
        questionDice = new QuestionDice();
        questionDice.setBounds(150, 100, 64, 64); // מיקום הקובייה

        // הוספת ה-QuestionDice ל-PANEL
        setLayout(null); // לayout חופשי
        add(questionDice);
    }

    // אם נדרש להפעיל את הקובייה
    public void rollDice() {
        questionDice.roll(); // להפעיל את הקובייה ולגלגל אותה
    }
}
